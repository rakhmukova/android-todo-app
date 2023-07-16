package com.example.todoapp.data.util

import com.example.todoapp.data.model.TodoItem

sealed class ChangeItemAction {
    class Add(val todoItemId: String) : ChangeItemAction()
    class Update(val todoItemId: String) : ChangeItemAction()
    class Delete(val todoItem: TodoItem?) : ChangeItemAction()
}
