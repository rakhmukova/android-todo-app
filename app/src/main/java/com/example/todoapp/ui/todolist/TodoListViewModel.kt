package com.example.todoapp.ui.todolist

import androidx.lifecycle.*
import com.example.todoapp.data.DataState
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class TodoListViewModel(private val repository: TodoItemRepository) : ViewModel() {
    // todo: display snackbar
    private val _syncState: MutableStateFlow<DataState<Unit>> =
        MutableStateFlow(DataState.Loading())
    private val _showOnlyCompletedTasks: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _completedTasksCount: MutableStateFlow<Int> = MutableStateFlow(0)

    val todoItems = repository.todoItems
    val showOnlyCompletedTasks: StateFlow<Boolean>
        get() = _showOnlyCompletedTasks.asStateFlow()
    val completedTasksCount: StateFlow<Int>
        get() = _completedTasksCount.asStateFlow()

    init {
        viewModelScope.launch {
            repository.loadData()
        }
    }

    fun loadTodoItems() {
        viewModelScope.launch {
            repository.loadData().collect {
                _syncState.value = it
            }
        }
    }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.updateTodoItem(todoItem)
        }
    }

    fun updateChecked(todoItem: TodoItem, isChecked: Boolean) {
        val modifiedItem = todoItem.copy(isCompleted = isChecked, modifiedAt = Date())
        updateTodoItem(modifiedItem)
    }
}
