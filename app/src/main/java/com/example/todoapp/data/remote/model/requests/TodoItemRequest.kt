package com.example.todoapp.data.remote.model.requests

import com.example.todoapp.data.remote.model.ApiTodoItem

/**
 * Request class for creating, updating or removing an item from the list.
 */
data class TodoItemRequest(
    val element: ApiTodoItem
)
