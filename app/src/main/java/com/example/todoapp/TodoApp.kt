package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.viewmodel.additem.AddTodoItemViewModel
import com.example.todoapp.viewmodel.additem.AddTodoItemViewModelFactory
import com.example.todoapp.viewmodel.todolist.TodoListViewModel
import com.example.todoapp.viewmodel.todolist.TodoListViewModelFactory

class TodoApp: Application() {
    private val todoItemsRepository: TodoItemsRepository = TodoItemsRepository()

    val todoListViewModel: TodoListViewModel by lazy {
        TodoListViewModelFactory.getInstance(todoItemsRepository)
            .create(TodoListViewModel::class.java)
    }

    val addTodoItemViewModel: AddTodoItemViewModel by lazy {
        AddTodoItemViewModelFactory.getInstance(todoItemsRepository)
            .create(AddTodoItemViewModel::class.java)
    }
}
