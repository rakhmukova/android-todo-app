package com.example.todoapp.data.util

import com.example.todoapp.di.component.AppScope
import java.util.Date
import javax.inject.Inject

@AppScope
class DateConverter @Inject constructor() {
    fun fromDate(date: Date): Long {
        return date.time
    }

    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}
