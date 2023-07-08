package com.example.todoapp.di.module

import android.content.Context
import androidx.work.WorkManager
import com.example.todoapp.di.scope.AppScope
import dagger.Module
import dagger.Provides

/**
 * Dagger module for providing workers-related dependencies.
 */
@Module
class WorkerModule {
    @Provides
    @AppScope
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}
