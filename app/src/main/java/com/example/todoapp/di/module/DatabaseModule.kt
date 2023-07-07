package com.example.todoapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.AppDatabase
import com.example.todoapp.data.local.dao.TodoItemDao
import com.example.todoapp.di.component.AppScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideTodoItemDao(appDatabase: AppDatabase): TodoItemDao = appDatabase.todoItemDao()

    @Provides
    @AppScope
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            // drop database when changing schema
            .fallbackToDestructiveMigration()
            .build()
    }
}
