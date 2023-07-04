package com.example.todoapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoItemRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: TodoItemRepository): ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }

        companion object {
            private var instance: MainViewModelFactory? = null

            fun getInstance(
                repository: TodoItemRepository
            ): MainViewModelFactory {
                return instance ?: MainViewModelFactory(repository).also {
                    instance = it
                }
            }
        }
}
