package com.example.todoapp.ui.todolist.recyclerview

import com.example.todoapp.data.model.TodoItem

/**
 * Callback interface for handling changes to items in the RecyclerView.
 */
interface TodoItemChangeCallbacks {
    fun onTodoItemClicked(todoItem: TodoItem)
    fun onTodoItemCheckedChanged(todoItem: TodoItem, isChecked: Boolean, position: Int)
}
