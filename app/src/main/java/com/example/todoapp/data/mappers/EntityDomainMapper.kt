package com.example.todoapp.data.mappers

import com.example.todoapp.data.local.entities.TodoItemEntity
import com.example.todoapp.data.model.TodoItem

class EntityDomainMapper {
    companion object {
        fun toDomainModel(todoItemEntity: TodoItemEntity): TodoItem {
            return TodoItem(
                id = todoItemEntity.id,
                text = todoItemEntity.text,
                priority = todoItemEntity.priority,
                deadline = todoItemEntity.deadline,
                isCompleted = todoItemEntity.isCompleted,
                createdAt = todoItemEntity.createdAt,
                modifiedAt = todoItemEntity.modifiedAt,
                isDeleted = todoItemEntity.isDeleted
            )
        }

        fun toEntityModel(todoItem: TodoItem): TodoItemEntity {
            return TodoItemEntity(
                id = todoItem.id,
                text = todoItem.text,
                priority = todoItem.priority,
                deadline = todoItem.deadline,
                isCompleted = todoItem.isCompleted,
                createdAt = todoItem.createdAt,
                modifiedAt = todoItem.modifiedAt,
                isDeleted = todoItem.isDeleted
            )
        }
    }
}
