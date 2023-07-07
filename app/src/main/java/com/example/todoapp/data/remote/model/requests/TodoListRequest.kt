package com.example.todoapp.data.remote.model.requests

import com.example.todoapp.data.remote.model.ApiTodoItem

/**
 * Request class for updating a list of items.
 */
data class TodoListRequest(
    val list: List<ApiTodoItem>
)
