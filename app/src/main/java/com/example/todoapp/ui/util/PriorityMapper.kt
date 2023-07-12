package com.example.todoapp.ui.util

import android.nfc.FormatException
import com.example.todoapp.data.model.Priority

/**
 * Utility class for mapping priority enum values to string representations and vice versa.
 */
object PriorityMapper {
    fun mapToString(priority: Priority): String {
        return when (priority) {
            Priority.LOW -> "Low"
            Priority.COMMON -> "No"
            Priority.HIGH -> "High"
        }
    }

    fun mapToPriority(priority: String): Priority {
        return when (priority) {
            LOW -> Priority.LOW
            COMMON -> Priority.COMMON
            HIGH -> Priority.HIGH
            else -> {
                throw FormatException("Unrecognized priority type")
            }
        }
    }

    const val LOW = "Low"
    const val COMMON = "No"
    const val HIGH = "High"
}
