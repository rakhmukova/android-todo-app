package com.example.todoapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repository.TodoItemRepository

@Suppress("UNCHECKED_CAST")
class TodoListViewModelFactory(private val repository: TodoItemRepository):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository) as T
    }

    companion object {
        private var instance: TodoListViewModelFactory? = null

        fun getInstance(
            repository: TodoItemRepository
        ): TodoListViewModelFactory {
            return instance ?: TodoListViewModelFactory(repository).also {
                instance = it
            }
        }
    }
}
