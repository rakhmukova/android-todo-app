package com.example.todoapp.data.remote.model.responses

import com.example.todoapp.data.remote.model.ApiTodoItem
import com.google.gson.annotations.SerializedName

/**
 * Response class for a list of items.
 */
data class TodoListResponseBody(
    @SerializedName("status")
    val status: String,
    @SerializedName("revision")
    val revision: Int,
    @SerializedName("list")
    val list: List<ApiTodoItem>
)
