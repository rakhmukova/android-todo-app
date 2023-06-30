package com.example.todoapp.data.remote.model

data class TodoListResponse(
    val status: String,
    val list: List<ApiTodoItem>,
    val revision: Int
)
