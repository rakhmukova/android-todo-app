package com.example.todoapp.ui.todolist

import androidx.lifecycle.*
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.di.component.FragmentScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TodoListViewModel @Inject constructor(private val repository: TodoItemRepository) : ViewModel() {
    private val _showOnlyCompletedTasks: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _completedTasksCount: MutableStateFlow<Int> = MutableStateFlow(0)

    val todoItems = repository.todoItems
    val showOnlyCompletedTasks: StateFlow<Boolean>
        get() = _showOnlyCompletedTasks.asStateFlow()
    val completedTasksCount: StateFlow<Int>
        get() = _completedTasksCount.asStateFlow()

    init {
        loadTodoItems()

        viewModelScope.launch {
            todoItems.collectLatest { items ->
                _completedTasksCount.value = items.count { it.isCompleted }
            }
        }
    }

    fun loadTodoItems() {
        viewModelScope.launch {
            repository.loadData()
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
