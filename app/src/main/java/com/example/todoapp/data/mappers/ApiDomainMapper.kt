package com.example.todoapp.data.mappers

import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.ApiTodoItem
import com.example.todoapp.data.util.DateConverter
import com.example.todoapp.data.util.StringPriorityMapper
import dagger.Reusable
import javax.inject.Inject

/**
 * Mapper class for converting API models to domain models of TodoItem and vice versa.
 */
@Reusable
class ApiDomainMapper @Inject constructor(
    private val dateConverter: DateConverter
) {
    fun toDomainModel(apiTodoItem: ApiTodoItem): TodoItem {
        return TodoItem(
            id = apiTodoItem.id,
            text = apiTodoItem.text,
            priority = StringPriorityMapper.toPriority(apiTodoItem.priority),
            deadline = apiTodoItem.deadline?.let { dateConverter.toDate(it) },
            isCompleted = apiTodoItem.isCompleted,
            createdAt = dateConverter.toDate(apiTodoItem.createdAt),
            modifiedAt = dateConverter.toDate(apiTodoItem.modifiedAt)
        )
    }

    fun toApiModel(todoItem: TodoItem, deviceId: String): ApiTodoItem {
        return ApiTodoItem(
            id = todoItem.id,
            text = todoItem.text,
            priority = StringPriorityMapper.fromPriority(todoItem.priority),
            deadline = todoItem.deadline?.let { dateConverter.fromDate(it) },
            isCompleted = todoItem.isCompleted,
            createdAt = dateConverter.fromDate(todoItem.createdAt),
            modifiedAt = dateConverter.fromDate(todoItem.modifiedAt),
            color = "#FFFFFF",
            lastUpdatedBy = deviceId
        )
    }
}
