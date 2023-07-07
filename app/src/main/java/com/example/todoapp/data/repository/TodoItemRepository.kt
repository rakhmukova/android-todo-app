package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.DataState
import com.example.todoapp.data.local.LocalDataSource
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.api.RemoteDataSource
import com.example.todoapp.data.remote.exceptions.ApiException
import com.example.todoapp.di.component.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AppScope
class TodoItemRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    // todo: create repo to handle different errors?
    private val _syncWithBackend = MutableStateFlow(true)
    val syncWithBackend: StateFlow<Boolean>
        get() = _syncWithBackend

    private val _todoItems = localDataSource.getTodoItemsFlow()
    val todoItems: Flow<List<TodoItem>>
        get() = _todoItems

    private val _changeItemState = MutableStateFlow<DataState<ChangeItemAction>>(DataState.Loading())
    val changeItemState: StateFlow<DataState<ChangeItemAction>>
        get() = _changeItemState

    private val _authState = MutableStateFlow(true)
    val authState: StateFlow<Boolean>
        get() = _authState

    private suspend fun tryAndHandleNetworkException(
        block: suspend () -> Unit,
        unauthorizedErrorBlock: suspend (Exception) -> Unit = {},
        notSyncDataErrorBlock: suspend (Exception) -> Unit = {},
        networkErrorBlock: suspend (Exception) -> Unit = {},
        errorBlock: suspend (Exception) -> Unit = {}
    ) = withContext(Dispatchers.IO) {
        // todo: make errorBlock run on other errors
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
            },
            unauthorizedErrorBlock = {
                _authState.value = false
            }
        )
    }

    // todo: generalize code
    suspend fun addTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.addTodoItem(todoItem)
            remoteDataSource.addTodoItem(todoItem)
            _changeItemState.value = DataState.Success(ChangeItemAction.ADD)
        }
        tryAndHandleNetworkException(
            block = block,
            networkErrorBlock = {
                _changeItemState.value = DataState.Error(it, ChangeItemAction.ADD)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            },
            unauthorizedErrorBlock = {
                _authState.value = false
            }
        )
    }

    suspend fun removeTodoItem(itemId: String) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.removeTodoItem(itemId)
            remoteDataSource.removeTodoItem(itemId)
            _changeItemState.value = DataState.Success(ChangeItemAction.DELETE)
        }
        tryAndHandleNetworkException(
            block = block,
            networkErrorBlock = {
                _changeItemState.value = DataState.Error(it, ChangeItemAction.DELETE)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            },
            unauthorizedErrorBlock = {
                _authState.value = false
            }
        )
    }

    suspend fun updateTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        val block: suspend () -> Unit = {
            localDataSource.updateTodoItem(todoItem)
            remoteDataSource.updateTodoItem(todoItem)
            _changeItemState.value = DataState.Success(ChangeItemAction.UPDATE)
        }
        tryAndHandleNetworkException(
            block = block,
            networkErrorBlock = {
                _changeItemState.value = DataState.Error(it, ChangeItemAction.UPDATE)
            },
            notSyncDataErrorBlock = {
                tryAndHandleNetworkException(
                    block = {
                        syncTodoItems()
                        block()
                    }
                )
            },
            unauthorizedErrorBlock = {
                _authState.value = false
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
