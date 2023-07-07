package com.example.todoapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.todoapp.di.component.AppScope
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {
    @Provides
    @AppScope
    fun provideSharedPreferences(
        context: Context
    ): SharedPreferences =
        context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
}
