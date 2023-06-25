package com.example.todoapp.viewmodel.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoItemsRepository

@Suppress("UNCHECKED_CAST")
class TodoListViewModelFactory(private val repository: TodoItemsRepository):
    ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository) as T
    }

    companion object {
        private var instance: TodoListViewModelFactory? = null

        fun getInstance(
            repository: TodoItemsRepository
        ): TodoListViewModelFactory {
            return instance ?: TodoListViewModelFactory(repository).also {
                instance = it
            }
        }
    }
}
