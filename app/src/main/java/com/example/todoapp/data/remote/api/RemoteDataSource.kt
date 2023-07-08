package com.example.todoapp.data.remote.api

import android.util.Log
import com.example.todoapp.data.mappers.ApiDomainMapper
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.requests.TodoItemRequestBody
import com.example.todoapp.data.remote.model.requests.TodoListRequestBody
import com.example.todoapp.data.remote.model.responses.TodoItemResponseBody
import com.example.todoapp.data.remote.model.responses.TodoListResponseBody
import com.example.todoapp.di.scope.AppScope
import javax.inject.Inject

/**
 * Remote data source responsible for making API calls to retrieve and update items.
 */
@AppScope
class RemoteDataSource @Inject constructor(
    private val apiService: TodoApiService,
    private val apiDomainMapper: ApiDomainMapper
) {
    // todo: probably create revision interceptor
    @Volatile
    private var _revision: Int = 0

    private fun updateRevision(body: TodoItemResponseBody) {
        _revision = body.revision
        Log.d(TAG, "revision: $_revision")
    }

    private fun updateRevision(body: TodoListResponseBody) {
        _revision = body.revision
        Log.d(TAG, "revision: $_revision")
    }

    suspend fun getTodoItems(): List<TodoItem> {
        val response = apiService.getAllTodoItems()
        val body = response.body()
        body?.let { updateRevision(it) }
        return body?.list?.map(apiDomainMapper::toDomainModel) ?: emptyList()
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        apiService.updateTodoItems(_revision, TodoListRequestBody(todoItems.map(apiDomainMapper::toApiModel)))
    }

    suspend fun addTodoItem(todoItem: TodoItem) {
        val response = apiService.addTodoItem(
            _revision,
            TodoItemRequestBody(apiDomainMapper.toApiModel(todoItem))
        )
        response.body()?.let { updateRevision(it) }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        val response = apiService.updateTodoItem(
            todoItem.id,
            TodoItemRequestBody(apiDomainMapper.toApiModel(todoItem))
        )
        response.body()?.let { updateRevision(it) }
    }

    suspend fun removeTodoItem(itemId: String) {
        val response = apiService.removeTodoItem(itemId)
        response.body()?.let { updateRevision(it) }
    }

    companion object {
        private const val TAG = "RemoteDataSource"
    }
}
