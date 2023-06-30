package com.example.todoapp.data.remote.model

data class TodoItemResponse(
    val status: String,
    val element: ApiTodoItem,
    val revision: Int
)
