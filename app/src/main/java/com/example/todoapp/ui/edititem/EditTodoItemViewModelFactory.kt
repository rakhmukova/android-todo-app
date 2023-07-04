package com.example.todoapp.ui.edititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoItemRepository

@Suppress("UNCHECKED_CAST")
class EditTodoItemViewModelFactory(private val repository: TodoItemRepository):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return EditTodoItemViewModel(repository) as T
    }

    companion object {
        private var instance: EditTodoItemViewModelFactory? = null

        fun getInstance(
            repository: TodoItemRepository
        ): EditTodoItemViewModelFactory {
            return instance ?: EditTodoItemViewModelFactory(repository).also {
                instance = it
            }
        }
    }
}
