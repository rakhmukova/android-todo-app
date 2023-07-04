package com.example.todoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.local.entities.TodoItemEntity
import com.example.todoapp.data.local.dao.TodoItemDao

private const val DB_NAME = "todo-database"

@Database(entities = [TodoItemEntity::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            )
                // drop database when changing schema
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
