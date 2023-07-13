package com.example.todoapp.data.remote.model.responses

import com.example.todoapp.data.remote.model.ApiTodoItem
import com.google.gson.annotations.SerializedName

/**
 * Response class for a single item.
 */
data class TodoItemResponseBody(
    @SerializedName("status")
    val status: String,
    @SerializedName("revision")
    val revision: Int,
    @SerializedName("element")
    val element: ApiTodoItem
)
