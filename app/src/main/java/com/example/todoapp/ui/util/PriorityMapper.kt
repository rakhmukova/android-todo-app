package com.example.todoapp.ui.util

import com.example.todoapp.data.model.Priority

/**
 * Utility class for mapping priority enum values to string representations.
 */
object PriorityMapper {
    fun mapToString(priority: Priority): String {
        return when (priority) {
            Priority.LOW -> "Low"
            Priority.COMMON -> "No"
            Priority.HIGH -> "High"
        }
    }
}
