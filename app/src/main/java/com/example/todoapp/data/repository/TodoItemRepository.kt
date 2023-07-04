package com.example.todoapp.data.repository

import android.content.Context
import com.example.todoapp.data.DataState
import com.example.todoapp.data.remote.RetrofitProvider
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.LocalDataSource
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.ErrorConverterCallAdapterFactory
import com.example.todoapp.data.remote.api.RemoteDataSource
import com.example.todoapp.data.remote.api.TodoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class TodoItemRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    private val _syncWithBackend = MutableStateFlow(false)
    val syncWithBackend: Flow<Boolean>
        get() = _syncWithBackend

    private val _todoItems = localDataSource.getTodoItemsFlow()
    val todoItems: Flow<List<TodoItem>>
        get() = _todoItems

    private val _addTodoItemState = MutableStateFlow<DataState<Unit>>(DataState.Loading())
    val addTodoItemState: StateFlow<DataState<Unit>>
        get() = _addTodoItemState

    private val _removeTodoItemState = MutableStateFlow<DataState<Unit>>(DataState.Loading())
    val removeTodoItemState: StateFlow<DataState<Unit>>
        get() = _removeTodoItemState

    private val _updateTodoItemState = MutableStateFlow<DataState<Unit>>(DataState.Loading())
    val updateTodoItemState: StateFlow<DataState<Unit>>
        get() = _updateTodoItemState

    suspend fun loadData() = withContext(Dispatchers.IO){
        try {
            val items = remoteDataSource.getTodoItems()
            localDataSource.updateTodoItems(items)
            _syncWithBackend.value = true
        } catch (e: Throwable) {
            _syncWithBackend.value = false
        }
    }

    suspend fun syncTodoItems() = withContext(Dispatchers.IO) {
        try {
            val remoteItems = remoteDataSource.getTodoItems()
            localDataSource.updateTodoItems(remoteItems)
            val localItems = localDataSource.getTodoItems()
            remoteDataSource.updateTodoItems(localItems)
            _syncWithBackend.value = true
        } catch (e: Exception) {
            _syncWithBackend.value = false
        }
    }

    suspend fun addTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        try {
            localDataSource.addTodoItem(todoItem)
            remoteDataSource.addTodoItem(todoItem)
            _addTodoItemState.value = DataState.Success(Unit)
        } catch (e: ErrorConverterCallAdapterFactory.NotSynchronizedDataException){
            // todo: handle wrong revision - syncTodoItems
        } catch (e: Throwable) {
            _addTodoItemState.value = DataState.Error(e)
        }
    }

    suspend fun removeTodoItem(itemId: String) = withContext(Dispatchers.IO) {
        try {
            localDataSource.removeTodoItem(itemId)
            remoteDataSource.removeTodoItem(itemId)
            _removeTodoItemState.value = DataState.Success(Unit)
        } catch (e: ErrorConverterCallAdapterFactory.NotSynchronizedDataException){
            // todo: handle wrong revision - syncTodoItems
        } catch (e: Throwable) {
            _removeTodoItemState.value = DataState.Error(e)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) = withContext(Dispatchers.IO) {
        try {
            localDataSource.updateTodoItem(todoItem)
            remoteDataSource.updateTodoItem(todoItem)
            _updateTodoItemState.value = DataState.Success(Unit)
        } catch (e: ErrorConverterCallAdapterFactory.NotSynchronizedDataException){
            // todo: handle wrong revision - syncTodoItems
        } catch (e: Throwable) {
            _updateTodoItemState.value = DataState.Error(e)
        }
    }

    suspend fun findById(itemId: String): TodoItem? = withContext(Dispatchers.IO) {
        return@withContext localDataSource.findById(itemId)
    }

    companion object {
        fun create(applicationContext: Context): TodoItemRepository {
            val retrofit = RetrofitProvider.create()
            val apiService = retrofit.create(TodoApiService::class.java)
            val remoteDataSource = RemoteDataSource(apiService)

            val appDatabase = AppDatabase.create(applicationContext)
            val todoItemDao = appDatabase.todoItemDao()
            val localDataSource = LocalDataSource(todoItemDao)

            return TodoItemRepository(localDataSource, remoteDataSource)
        }
    }
}
