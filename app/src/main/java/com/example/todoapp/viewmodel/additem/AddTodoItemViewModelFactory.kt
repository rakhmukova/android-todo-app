package com.example.todoapp.viewmodel.additem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoItemsRepository

@Suppress("UNCHECKED_CAST")
class AddTodoItemViewModelFactory(private val repository: TodoItemsRepository):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return AddTodoItemViewModel(repository) as T
    }

    companion object {
        private var instance: AddTodoItemViewModelFactory? = null

        fun getInstance(
            repository: TodoItemsRepository
        ): AddTodoItemViewModelFactory {
            return instance ?: AddTodoItemViewModelFactory(repository).also {
                instance = it
            }
        }
    }
}
