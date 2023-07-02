package com.example.todoapp.data.remote.model

open class TodoResponse(
    val status: String,
    val revision: Int
) {
    class Item(
        status: String,
        val element: ApiTodoItem,
        revision: Int
    ): TodoResponse(status, revision)

    class List(
        status: String,
        val list: kotlin.collections.List<ApiTodoItem>,
        revision: Int
    ): TodoResponse(status, revision)
}
