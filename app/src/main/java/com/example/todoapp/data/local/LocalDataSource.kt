package com.example.todoapp.data.local

import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.data.local.entities.TodoItemEntity
import com.example.todoapp.data.mappers.EntityDomainMapper
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.di.component.AppScope
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AppScope
class LocalDataSource @Inject constructor(
    private val todoItemDao: TodoItemDao,
    private val entityDomainMapper: EntityDomainMapper
) {

    private fun List<TodoItemEntity>.processEntities(): List<TodoItem> {
        return this
            .map(entityDomainMapper::toDomainModel)
            .filter { !it.isDeleted }
    }

    fun getTodoItemsFlow() = todoItemDao.getTodoItemsFlow().map {
        it.processEntities()
    }

    suspend fun getTodoItems() = todoItemDao.getTodoItems().processEntities()

    suspend fun findById(itemId: String): TodoItem? {
        return todoItemDao.findById(itemId)?.let(entityDomainMapper::toDomainModel)
    }

    suspend fun updateTodoItems(todoItems: List<TodoItem>) {
        todoItemDao.updateTodoItems(
            todoItems.map {
                entityDomainMapper.toEntityModel(it)
            }
        )
    }

    suspend fun addTodoItem(todoItem: TodoItem) {
        todoItemDao.insertTodoItem(entityDomainMapper.toEntityModel(todoItem))
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        todoItemDao.updateTodoItem(entityDomainMapper.toEntityModel(todoItem))
    }

    suspend fun removeTodoItem(itemId: String) {
        todoItemDao.markItemDeleted(itemId)
    }
}
