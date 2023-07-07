package com.example.todoapp.data.mappers

import android.nfc.FormatException
import com.example.todoapp.data.util.DeviceIdManager
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.ApiTodoItem
import com.example.todoapp.data.util.DateConverter
import com.example.todoapp.di.component.AppScope
import javax.inject.Inject

@AppScope
class ApiDomainMapper @Inject constructor(
    private val deviceIdManager: DeviceIdManager,
    private val dateConverter: DateConverter
) {
    fun toDomainModel(apiTodoItem: ApiTodoItem): TodoItem {
        return TodoItem(
            id = apiTodoItem.id,
            text = apiTodoItem.text,
            priority = stringToPriority(apiTodoItem.priority),
            deadline = apiTodoItem.deadline?.let { dateConverter.toDate(it) },
            isCompleted = apiTodoItem.isCompleted,
            createdAt = dateConverter.toDate(apiTodoItem.createdAt),
            modifiedAt = dateConverter.toDate(apiTodoItem.modifiedAt)
        )
    }

    fun toApiModel(todoItem: TodoItem): ApiTodoItem {
        return ApiTodoItem(
            id = todoItem.id,
            text = todoItem.text,
            priority = priorityToString(todoItem.priority),
            deadline = todoItem.deadline?.let { dateConverter.fromDate(it) },
            isCompleted = todoItem.isCompleted,
            createdAt = dateConverter.fromDate(todoItem.createdAt),
            modifiedAt = dateConverter.fromDate(todoItem.modifiedAt),
            color = "#FFFFFF",
            lastUpdatedBy = deviceIdManager.getDeviceId()
        )
    }

    private fun stringToPriority(s: String): Priority {
        return when (s) {
            "low" -> Priority.LOW
            "basic" -> Priority.COMMON
            "important" -> Priority.HIGH
            else -> {
                throw FormatException("Unrecognised Priority Type")
            }
        }
    }

    private fun priorityToString(priority: Priority): String {
        return when (priority) {
            Priority.HIGH -> "important"
            Priority.COMMON -> "basic"
            Priority.LOW -> "low"
        }
    }
}
