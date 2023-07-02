package com.example.todoapp.data.remote.api

import com.example.todoapp.data.mappers.ApiDomainMapper
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.TodoItemRequest
import com.example.todoapp.data.remote.model.TodoItemResponse
import com.example.todoapp.data.remote.model.TodoListRequest
import retrofit2.Response

class RemoteDataSource(private val apiService: TodoApiService) {
    @Volatile
    private var _revision: Int = 0

    suspend fun getTodoItems(): List<TodoItem> {
        val response = apiService.getAllTodoItems()
        val body = response.body()
        if (body != null){
            _revision = body.revision
            return body.list.map(ApiDomainMapper::toDomainModel)
        }

        return emptyList()
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        apiService.updateTodoItems(_revision, TodoListRequest(todoItems.map(ApiDomainMapper::toApiModel)))
    }

    suspend fun addTodoItem(todoItem: TodoItem): Response<TodoItemResponse> {
        return apiService.addTodoItem(_revision,
            TodoItemRequest(ApiDomainMapper.toApiModel(todoItem)))
    }

    suspend fun updateTodoItem(todoItem: TodoItem): Response<TodoItemResponse> {
        return apiService.updateTodoItem(todoItem.id,
            TodoItemRequest(ApiDomainMapper.toApiModel(todoItem)))
    }

    suspend fun removeTodoItem(itemId: String) {
        apiService.removeTodoItem(itemId)
    }

    companion object {
        private const val TAG = "RemoteDataSource"
    }
}
