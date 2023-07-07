package com.example.todoapp.data.remote.api

import android.util.Log
import com.example.todoapp.data.mappers.ApiDomainMapper
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.requests.TodoItemRequest
import com.example.todoapp.data.remote.model.requests.TodoListRequest
import com.example.todoapp.data.remote.model.responses.TodoResponse
import com.example.todoapp.di.scope.AppScope
import retrofit2.Response
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

    private fun updateRevision(response: Response<out TodoResponse>) {
        val body = response.body()
        if (body != null) {
            _revision = body.revision
            Log.d(TAG, "revision: $_revision")
        }
    }

    suspend fun getTodoItems(): List<TodoItem> {
        val response = apiService.getAllTodoItems()
        updateRevision(response)
        return response.body()?.list?.map(apiDomainMapper::toDomainModel) ?: emptyList()
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        apiService.updateTodoItems(_revision, TodoListRequest(todoItems.map(apiDomainMapper::toApiModel)))
    }

    suspend fun addTodoItem(todoItem: TodoItem): Response<TodoResponse.Item> {
        val response = apiService.addTodoItem(
            _revision,
            TodoItemRequest(apiDomainMapper.toApiModel(todoItem))
        )
        updateRevision(response)
        return response
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Response<TodoResponse.Item> {
        val response = apiService.updateTodoItem(
            todoItem.id,
            TodoItemRequest(apiDomainMapper.toApiModel(todoItem))
        )
        updateRevision(response)
        return response
    }

    suspend fun removeTodoItem(itemId: String): Response<TodoResponse.Item> {
        val response = apiService.removeTodoItem(itemId)
        updateRevision(response)
        return response
    }

    companion object {
        private const val TAG = "RemoteDataSource"
    }
}
