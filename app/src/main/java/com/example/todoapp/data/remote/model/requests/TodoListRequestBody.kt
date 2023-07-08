package com.example.todoapp.data.remote.model.requests

import com.example.todoapp.data.remote.model.ApiTodoItem
import com.google.gson.annotations.SerializedName

/**
 * Request class for updating a list of items.
 */
data class TodoListRequestBody(
    @SerializedName("list")
    val list: List<ApiTodoItem>
)
