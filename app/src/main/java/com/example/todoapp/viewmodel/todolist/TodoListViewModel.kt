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

    val todoItems: StateFlow<List<TodoItem>> = _todoItems.asStateFlow()
    val filteredTodoItems: StateFlow<List<TodoItem>> = _filteredTodoItems.asStateFlow()
    val showOnlyCompletedTasks: StateFlow<Boolean> = _showOnlyCompletedTasks.asStateFlow()

    init {
        loadTodoItems()
    }

    // TODO: fix
    fun countCompletedTasks(): Int = _todoItems.value.count { it.isCompleted }

    private fun updateTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            repository.update(todoItem)
        }
    }

    fun setShowOnlyCompletedTasks(value: Boolean) {
        _showOnlyCompletedTasks.value = value
    }

    fun updateChecked(todoItem: TodoItem, isChecked: Boolean) {
        val modifiedItem = todoItem.copy(isCompleted = isChecked, modifiedAt = Date())
        updateTodoItem(modifiedItem)
        filterTodoItems(_showOnlyCompletedTasks.value)
    }

    private fun loadTodoItems() {
        viewModelScope.launch {
            repository.getAll().collect { todoItems ->
                _todoItems.value = todoItems
            }
        }
    }

    fun filterTodoItems(showOnlyCompletedTasks: Boolean) {
        val allItems = _todoItems.value
        val filteredTodoItems = if (showOnlyCompletedTasks) {
            allItems.filter { !it.isCompleted }
        } else {
            allItems
        }
        _filteredTodoItems.value = filteredTodoItems
    }
}
