package com.example.todoapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model class for the MainActivity.
 */
class MainViewModel @Inject constructor(
    private val repository: TodoItemRepository
) : ViewModel() {
    val changeItemState = repository.changeItemState
    val syncWithBackend = repository.syncWithBackend

    fun recoverTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.recoverTodoItem(todoItem)
        }
    }
}
