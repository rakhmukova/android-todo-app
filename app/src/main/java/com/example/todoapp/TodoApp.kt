package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.DeviceIdManager
import com.example.todoapp.data.remote.ConnectivityMonitoring
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.ui.edititem.EditTodoItemViewModel
import com.example.todoapp.ui.edititem.EditTodoItemViewModelFactory
import com.example.todoapp.ui.todolist.TodoListViewModel
import com.example.todoapp.ui.todolist.TodoListViewModelFactory
import com.example.todoapp.workers.WorkerProvider
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
        WorkerProvider.setupWorkers(applicationContext)
        DeviceIdManager.loadDeviceId(applicationContext)
    }

    private fun setupConnectivityMonitoring(todoItemRepository: TodoItemRepository) {
        val connectivityMonitoring = ConnectivityMonitoring()
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        connectivityMonitoring.setupNetworkListener(applicationContext, todoItemRepository, coroutineScope)
    }
}
