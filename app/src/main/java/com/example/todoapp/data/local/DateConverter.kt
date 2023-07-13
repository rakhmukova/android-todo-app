package com.example.todoapp.data.local

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter class for Date objects used in database.
 */
class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
