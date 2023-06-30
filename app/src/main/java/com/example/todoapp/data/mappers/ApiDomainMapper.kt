package com.example.todoapp.data.mappers

import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.remote.model.ApiTodoItem
import java.lang.Exception

class ApiDomainMapper {
    companion object {
        fun toDomainModel(apiTodoItem: ApiTodoItem): TodoItem {
            return TodoItem(
                id = apiTodoItem.id,
                text = apiTodoItem.text,
                priority = stringToPriority(apiTodoItem.priority),
                deadline = apiTodoItem.deadline?.let { DateConverter.longToDate(it) },
                isCompleted = apiTodoItem.isCompleted,
                createdAt = DateConverter.longToDate(apiTodoItem.createdAt),
                modifiedAt = DateConverter.longToDate(apiTodoItem.modifiedAt)
            )
        }

        fun toApiModel(todoItem: TodoItem): ApiTodoItem {
            return ApiTodoItem(
                id = todoItem.id,
                text = todoItem.text,
                priority = priorityToString(todoItem.priority),
                deadline = todoItem.deadline?.let { DateConverter.dateToLong(it) },
                isCompleted = todoItem.isCompleted,
                createdAt = DateConverter.dateToLong(todoItem.createdAt),
                modifiedAt = DateConverter.dateToLong(todoItem.createdAt),
                color = "#FFFFFF",
                // todo: store device unique id
                lastUpdatedBy = ""
            )
        }

        private fun stringToPriority(s: String): Priority {
            return when (s) {
                "low" -> Priority.LOW
                "basic" -> Priority.COMMON
                "important" -> Priority.HIGH
                else -> {
                    throw Exception("Unrecognised Priority Type")
                }
            }
        }

        private fun priorityToString(priority: Priority) : String {
            return when (priority) {
                Priority.HIGH -> "important"
                Priority.COMMON -> "basic"
                Priority.LOW -> "low"
            }
        }
    }
}
