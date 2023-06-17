package com.example.todoapp.util

import com.example.todoapp.data.model.Priority

class PriorityMapper {
    companion object {
        fun mapToString(priority: Priority): String {
            return when (priority) {
                Priority.LOW -> "Low"
                Priority.COMMON -> "No"
                Priority.HIGH -> "High"
            }
        }
    }
}
