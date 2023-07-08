package com.example.todoapp.ui.edititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * ViewModel class for editing an item.
 */
class EditTodoItemViewModel @Inject constructor(private val repository: TodoItemRepository) : ViewModel() {

    private val _todoItem = MutableStateFlow(TodoItem())
    val todoItem: StateFlow<TodoItem> = _todoItem.asStateFlow()

    private var _isExisting: Boolean? = null

    fun updateDescription(description: String) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(text = description)
        _todoItem.value = updatedTodoItem
    }

    fun updatePriority(priority: Priority) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(priority = priority)
        _todoItem.value = updatedTodoItem
    }

    fun updateDeadline(deadline: Date?) {
        val currentTodoItem = _todoItem.value
        val updatedTodoItem = currentTodoItem.copy(deadline = deadline)
        _todoItem.value = updatedTodoItem
    }

    fun saveTodoItem() {
        val modifiedAt = Date()
        val currentTodoItem = _todoItem.value.copy(modifiedAt = modifiedAt)
        if (_isExisting == true) {
            viewModelScope.launch {
                repository.updateTodoItem(currentTodoItem)
            }
        } else {
            viewModelScope.launch {
                repository.addTodoItem(currentTodoItem.copy(createdAt = modifiedAt))
            }
        }
    }

    fun removeTodoItem() {
        val currentTodoItem = _todoItem.value
        viewModelScope.launch {
            repository.removeTodoItem(currentTodoItem.id)
        }
    }

    fun createOrFind(id: String?) {
        if (id == null) {
            _isExisting = false
            _todoItem.value = TodoItem()
        } else {
            _isExisting = true
            viewModelScope.launch {
                val todoItem = repository.findById(id)
                if (todoItem == null) {
                    _todoItem.value = TodoItem()
                } else {
                    _todoItem.value = todoItem
                }
            }
        }
    }
}
