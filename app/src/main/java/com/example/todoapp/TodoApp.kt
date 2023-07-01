package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.remote.ConnectivityMonitoring
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.viewmodel.edititem.EditTodoItemViewModel
import com.example.todoapp.viewmodel.edititem.EditTodoItemViewModelFactory
import com.example.todoapp.viewmodel.todolist.TodoListViewModel
import com.example.todoapp.viewmodel.todolist.TodoListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class TodoApp : Application() {

    lateinit var todoListViewModel: TodoListViewModel
    lateinit var editTodoItemViewModel: EditTodoItemViewModel

    override fun onCreate() {
        super.onCreate()

        val todoItemRepository = TodoItemRepository.create(applicationContext)

        todoListViewModel = TodoListViewModelFactory.getInstance(todoItemRepository)
            .create(TodoListViewModel::class.java)

        editTodoItemViewModel = EditTodoItemViewModelFactory.getInstance(todoItemRepository)
            .create(EditTodoItemViewModel::class.java)

        setupConnectivityMonitoring(todoItemRepository)
    }

    private fun setupConnectivityMonitoring(todoItemRepository: TodoItemRepository) {
        val connectivityMonitoring = ConnectivityMonitoring()
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        connectivityMonitoring.setupNetworkListener(applicationContext, todoItemRepository, coroutineScope)
    }
}
