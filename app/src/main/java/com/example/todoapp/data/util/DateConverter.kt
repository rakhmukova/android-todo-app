package com.example.todoapp.data.util

import dagger.Reusable
import java.util.Date
import javax.inject.Inject

@Reusable
class DateConverter @Inject constructor() {
    fun fromDate(date: Date): Long {
        return date.time
    }

    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}
