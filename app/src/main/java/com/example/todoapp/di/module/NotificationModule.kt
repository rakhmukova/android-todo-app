package com.example.todoapp.di.module

import android.app.NotificationManager
import android.content.Context
import com.example.todoapp.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {
    @Provides
    @AppScope
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}
