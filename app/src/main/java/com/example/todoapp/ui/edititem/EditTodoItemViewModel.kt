package com.example.todoapp.ui.edititem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel class for editing an item.
 */
class EditTodoItemViewModel @AssistedInject constructor(
    private val repository: TodoItemRepository,
    @Assisted private val todoItemId: String
) : ViewModel() {

    private val _todoItem = MutableStateFlow(TodoItem())
    val todoItem: StateFlow<TodoItem> = _todoItem.asStateFlow()

    private val _screenState = MutableStateFlow(ScreenState.LOADING)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    var isExisting: Boolean = false
        private set

    init {
        viewModelScope.launch {
            createOrFind(todoItemId)
            _screenState.value = ScreenState.LOADED
        }
    }

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
        if (isExisting == true) {
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

    private suspend fun createOrFind(id: String) {
        if (id.isEmpty()) {
            isExisting = false
            _todoItem.value = TodoItem()
        } else {
            val todoItem = repository.findById(id)
            if (todoItem == null) {
                isExisting = false
                _todoItem.value = TodoItem()
            } else {
                isExisting = true
                _todoItem.value = todoItem
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(todoItemId: String): EditTodoItemViewModel
    }
}
