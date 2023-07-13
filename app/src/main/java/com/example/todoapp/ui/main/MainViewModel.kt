package com.example.todoapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.TodoItemRepository
import javax.inject.Inject

/**
 * View model class for the MainActivity.
 */
class MainViewModel @Inject constructor(repository: TodoItemRepository) : ViewModel() {
    val changeItemState = repository.changeItemState
    val syncWithBackend = repository.syncWithBackend
}
