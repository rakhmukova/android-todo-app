package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.data.local.entities.TodoItemEntity
import com.example.todoapp.di.component.AppScope

/**
 * Database class for the TodoApp application.
 */
@AppScope
@Database(entities = [TodoItemEntity::class], version = 7, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        const val DB_NAME = "todo-database"
    }
}
