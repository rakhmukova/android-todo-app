package com.example.todoapp.data.remote.model.requests

import com.example.todoapp.data.remote.model.ApiTodoItem

data class TodoListRequest(
    val list: List<ApiTodoItem>
)
