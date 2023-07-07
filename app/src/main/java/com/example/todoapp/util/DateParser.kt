package com.example.todoapp.util

import java.text.SimpleDateFormat
import java.util.*

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
