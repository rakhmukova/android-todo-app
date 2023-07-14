package com.example.todoapp.ui.util

import android.content.Context
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority

/**
 * Utility class for mapping priority enum values to string resource.
 */
object PriorityMapper {
    fun mapToString(priority: Priority, context: Context): String {
        return when (priority) {
            Priority.LOW -> context.getString(R.string.low_priority)
            Priority.COMMON -> context.getString(R.string.no_priority)
            Priority.HIGH -> context.getString(R.string.high_priority)
        }
    }
}
