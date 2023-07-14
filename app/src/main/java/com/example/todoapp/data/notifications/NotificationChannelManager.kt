package com.example.todoapp.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import javax.inject.Inject

class NotificationChannelManager @Inject constructor(private val notificationManager: NotificationManager) {
    fun createNotificationChannel(
        channelDescription: String,
        channelName: String,
        channelId: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = channelDescription
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
