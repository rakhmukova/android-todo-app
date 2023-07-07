package com.example.todoapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.TodoItemRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(repository: TodoItemRepository) : ViewModel() {
    val addTodoItemState = repository.addTodoItemState
    val removeTodoItemState = repository.removeTodoItemState
    val updateTodoItemState = repository.updateTodoItemState
    val syncWithBackend = repository.syncWithBackend
}
