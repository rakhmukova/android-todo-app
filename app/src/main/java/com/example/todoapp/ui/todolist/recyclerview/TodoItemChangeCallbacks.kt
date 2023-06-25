package com.example.todoapp.ui.todolist.recyclerview

import com.example.todoapp.data.model.TodoItem

interface TodoItemChangeCallbacks {
    fun onTodoItemClicked(todoItem: TodoItem)
    fun onTodoItemCheckedChanged(todoItem: TodoItem, isChecked: Boolean, position: Int)
}