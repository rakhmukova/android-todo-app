package com.example.todoapp.viewmodel.todolist

import androidx.lifecycle.*
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class TodoListViewModel(private val repository: TodoItemsRepository) : ViewModel() {
    private val _todoItems: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    private val _filteredTodoItems: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    private val _showOnlyCompletedTasks: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _completedTasksCount: MutableStateFlow<Int> = MutableStateFlow(0)

    val filteredTodoItems: StateFlow<List<TodoItem>> = _filteredTodoItems.asStateFlow()
    val showOnlyCompletedTasks: StateFlow<Boolean> = _showOnlyCompletedTasks.asStateFlow()
    val completedTasksCount: StateFlow<Int> = _completedTasksCount.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { todoItems ->
                _todoItems.value = todoItems
            }
        }

        viewModelScope.launch {
            _todoItems.collect { todoItems ->
                _todoItems.value = todoItems
                filterTodoItems()
                updateCompletedTasksCount()
            }
        }
    }

    private fun updateCompletedTasksCount() {
        val count = _todoItems.value.count { it.isCompleted }
        _completedTasksCount.value = count
    }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.update(todoItem)
        }
    }

    fun setShowOnlyCompletedTasks(value: Boolean) {
        _showOnlyCompletedTasks.value = value
        filterTodoItems()
    }

    fun updateChecked(todoItem: TodoItem, isChecked: Boolean) {
        val modifiedItem = todoItem.copy(isCompleted = isChecked, modifiedAt = Date())
        updateTodoItem(modifiedItem)
        // TODO: fix
        filterTodoItems()
        updateCompletedTasksCount()
    }

    private fun filterTodoItems() {
        val allItems = _todoItems.value
        val filteredTodoItems = if (showOnlyCompletedTasks.value) {
            allItems.filter { !it.isCompleted }
        } else {
            allItems
        }
        _filteredTodoItems.value = filteredTodoItems
    }
}
