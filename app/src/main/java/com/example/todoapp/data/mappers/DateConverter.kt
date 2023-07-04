package com.example.todoapp.data.mappers

import java.util.Date

class DateConverter {
    companion object {
        fun dateToLong(date: Date): Long {
            return date.time
        }

        fun longToDate(timestamp: Long): Date {
            return Date(timestamp)
        }
    }
}
