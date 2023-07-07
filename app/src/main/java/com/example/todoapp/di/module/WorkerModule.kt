package com.example.todoapp.di.module

import android.content.Context
import androidx.work.WorkManager
import com.example.todoapp.di.component.AppScope
import dagger.Module
import dagger.Provides

@Module
class WorkerModule {
    @Provides
    @AppScope
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}
