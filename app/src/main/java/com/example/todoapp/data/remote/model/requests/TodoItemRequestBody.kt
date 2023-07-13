package com.example.todoapp.data.remote.model.requests

import com.example.todoapp.data.remote.model.ApiTodoItem
import com.google.gson.annotations.SerializedName

/**
 * Request class for creating, updating or removing an item from the list.
 */
data class TodoItemRequestBody(
    @SerializedName("element")
    val element: ApiTodoItem
)
