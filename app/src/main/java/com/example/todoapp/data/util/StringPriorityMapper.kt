package com.example.todoapp.data.util

import android.nfc.FormatException
import com.example.todoapp.data.model.Priority

/**
 * Utility object for mapping priority values between String and Priority enum.
 */
object StringPriorityMapper {
    fun toPriority(s: String): Priority {
        return when (s) {
            LOW -> Priority.LOW
            COMMON -> Priority.COMMON
            HIGH -> Priority.HIGH
            else -> {
                throw FormatException("Unrecognised Priority Type")
            }
        }
    }

    fun fromPriority(priority: Priority): String {
        return when (priority) {
            Priority.LOW -> LOW
            Priority.COMMON -> COMMON
            Priority.HIGH -> HIGH
        }
    }

    private const val LOW = "low"
    private const val COMMON = "basic"
    private const val HIGH = "important"
}
