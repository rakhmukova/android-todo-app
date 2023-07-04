package com.example.todoapp.data.local

import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.data.local.entities.TodoItemEntity
import com.example.todoapp.data.mappers.EntityDomainMapper
import com.example.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.map

class LocalDataSource(private val todoItemDao: TodoItemDao) {

    private fun List<TodoItemEntity>.processEntities(): List<TodoItem> {
        return this
            .map(EntityDomainMapper::toDomainModel)
            .filter { ! it.isDeleted }
    }

    fun getTodoItemsFlow() = todoItemDao.getTodoItemsFlow().map {
        it.processEntities()
    }

    suspend fun getTodoItems() = todoItemDao.getTodoItems().processEntities()

    suspend fun findById(itemId: String): TodoItem? {
        return todoItemDao.findById(itemId)?.let(EntityDomainMapper::toDomainModel)
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        todoItemDao.updateTodoItems(todoItems.map {
            EntityDomainMapper.toEntityModel(it)
        })
    }

    suspend fun addTodoItem(todoItem: TodoItem) {
        todoItemDao.insertTodoItem(EntityDomainMapper.toEntityModel(todoItem))
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        todoItemDao.updateTodoItem(EntityDomainMapper.toEntityModel(todoItem))
    }

    suspend fun removeTodoItem(itemId: String) {
        todoItemDao.markItemDeleted(itemId)
    }
}
