package com.example.todoapp.data.util

sealed class ChangeItemAction(
    val todoItemId: String
) {
    class Add(todoItemId: String) : ChangeItemAction(todoItemId)
    class Delete(todoItemId: String) : ChangeItemAction(todoItemId)
    class Update(todoItemId: String) : ChangeItemAction(todoItemId)
}
