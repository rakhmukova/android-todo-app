package com.example.todoapp.ui.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class for parsing and formatting dates.
 */
object DateParser {
    fun parse(date: Date?): String {
        return if (date != null) {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            dateFormat.format(date)
        } else {
            ""
        }
    }
}
