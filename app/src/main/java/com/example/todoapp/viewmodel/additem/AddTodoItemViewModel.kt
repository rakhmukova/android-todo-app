package com.example.todoapp.viewmodel.additem

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class AddTodoItemViewModel(private val repository: TodoItemsRepository) : ViewModel() {

    private val _todoItem = MutableStateFlow(createTodoItem())
    val todoItem: StateFlow<TodoItem> = _todoItem.asStateFlow()

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
        val currentTodoItem = _todoItem.value
        repository.updateOrAdd(currentTodoItem)
    }

    fun removeTodoItem() {
        val currentTodoItem = _todoItem.value
        repository.remove(currentTodoItem)
    }

    fun createOrFind(id: String?) {
        if (id == null) {
            _todoItem.value = createTodoItem()
        } else {
            val todoItem = repository.findById(id)
            if (todoItem == null){
                _todoItem.value = createTodoItem()
            } else {
                _todoItem.value = todoItem
            }
        }
    }

    private fun createTodoItem(): TodoItem {
        return TodoItem(UUID.randomUUID().toString(), "", Priority.COMMON,
            Date(), false, Date(), Date())
    }
}
