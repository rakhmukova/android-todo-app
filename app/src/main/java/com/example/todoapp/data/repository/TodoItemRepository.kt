package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.DataResult
import com.example.todoapp.data.local.LocalDataSource
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.api.RemoteDataSource
import com.example.todoapp.data.remote.exceptions.ApiException
import com.example.todoapp.data.util.ChangeItemAction
import com.example.todoapp.data.util.ConnectivityMonitoring
import com.example.todoapp.data.util.NetworkStatus
import com.example.todoapp.di.scope.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class for managing todoItems.
 */
@AppScope
class TodoItemRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val connectivityMonitoring: ConnectivityMonitoring
) {
    private val _syncWithBackend = MutableStateFlow(true)
    val syncWithBackend: StateFlow<Boolean>
        get() = _syncWithBackend

    private val _todoItems = localDataSource.getTodoItemsFlow()
    val todoItems: Flow<List<TodoItem>>
        get() = _todoItems

    private val _changeItemState = MutableStateFlow<DataResult<ChangeItemAction>>(DataResult.Loading())
    val changeItemState: StateFlow<DataResult<ChangeItemAction>>
        get() = _changeItemState

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            connectivityMonitoring.networkStatus.collectLatest {
                if (it == NetworkStatus.AVAILABLE) {
                    syncTodoItems()
                }
            }
        }
    }

    private suspend fun tryAndHandleNetworkException(
        block: suspend () -> Unit,
        unauthorizedErrorBlock: suspend (Exception) -> Unit = {},
        notSyncDataErrorBlock: suspend (Exception) -> Unit = {},
        networkErrorBlock: suspend (Exception) -> Unit = {},
        errorBlock: suspend (Exception) -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        try {
            block()
            Log.d(TAG, "tryAndHandleNetworkException: Success")
        } catch (e: ApiException) {
            Log.d(TAG, "tryAndHandleNetworkException: ${e.message}", e)
            when (e) {
                is ApiException.UnauthorizedException -> {
                    unauthorizedErrorBlock(e)
                }
                is ApiException.NotSynchronizedDataException -> {
                    notSyncDataErrorBlock(e)
                }
                is ApiException.NetworkException -> {
                    networkErrorBlock(e)
                }
                else -> {}
            }
            // run error block always after specific error blocks
            errorBlock(e)
        } catch (e: Exception) {
            Log.d(TAG, "tryAndHandleNetworkException: ${e.message}", e)
            errorBlock(e)
        }
    }

    suspend fun loadData() = withContext(Dispatchers.IO) {
        tryAndHandleNetworkException(
            block = {
                val items = remoteDataSource.getTodoItems()
                localDataSource.updateTodoItems(items)
                _syncWithBackend.value = true
            },
            networkErrorBlock = {
                _syncWithBackend.value = false
            }
        )
    }

    suspend fun syncTodoItems() = withContext(Dispatchers.IO) {
        tryAndHandleNetworkException(
            block = {
                val remoteItems = remoteDataSource.getTodoItems()
                localDataSource.updateTodoItems(remoteItems)
                val localItems = localDataSource.getTodoItems()
                remoteDataSource.updateTodoItems(localItems)
                _syncWithBackend.value = true
            },
            networkErrorBlock = {
                _syncWithBackend.value = false
            }
        )
    }

    // todo: generalize code
    suspend fun addTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.addTodoItem(todoItem)
            remoteDataSource.addTodoItem(todoItem)
            _changeItemState.value = DataResult.Success(ChangeItemAction.Add(todoItem.id))
            Log.d(TAG, "addTodoItem: ${todoItem.id}")
        }
        tryAndHandleNetworkException(
            block = block,
            errorBlock = {
                _changeItemState.value = DataResult.Error(it, ChangeItemAction.Add(todoItem.id))
                Log.e(TAG, "addTodoItem: ${todoItem.id}", it)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            }
        )
    }

    suspend fun removeTodoItem(itemId: String) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.removeTodoItem(itemId)
            remoteDataSource.removeTodoItem(itemId)
            _changeItemState.value = DataResult.Success(ChangeItemAction.Delete(itemId))
            Log.d(TAG, "removeTodoItem: $itemId")
        }
        tryAndHandleNetworkException(
            block = block,
            errorBlock = {
                _changeItemState.value = DataResult.Error(it, ChangeItemAction.Delete(itemId))
                Log.e(TAG, "removeTodoItem: $itemId", it)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            }
        )
    }

    suspend fun updateTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.updateTodoItem(todoItem)
            remoteDataSource.updateTodoItem(todoItem)
            _changeItemState.value = DataResult.Success(ChangeItemAction.Update(todoItem.id))
            Log.d(TAG, "updateTodoItem: ${todoItem.id}")
        }
        tryAndHandleNetworkException(
            block = block,
            errorBlock = {
                _changeItemState.value = DataResult.Error(it, ChangeItemAction.Update(todoItem.id))
                Log.e(TAG, "updateTodoItem: ${todoItem.id}", it)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            }
        )
    }

    suspend fun findById(itemId: String): TodoItem? = withContext(Dispatchers.IO) {
        return@withContext localDataSource.findById(itemId)
    }

    companion object {
        private const val TAG = "TodoItemRepository"
    }
}
