package com.example.todoapp.data.notifications

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.data.local.LocalDataSource
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.ui.main.MainActivity
import com.example.todoapp.ui.util.PriorityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScope
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var localDataSource: LocalDataSource

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: context")
        ((context ?: return).applicationContext as TodoApp).appComponent.inject(this)
        val todoItemId = intent?.getStringExtra(NotificationConstants.TODO_ITEM_ID_KEY)
        if (todoItemId != null) {
            Log.d(TAG, "onReceive: $todoItemId")
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                val todoItem = localDataSource.findById(todoItemId)
                if (todoItem != null) {
                    notifyAboutDeadline(todoItem, context)
                }
            }
        }
    }

    private fun notifyAboutDeadline(todoItem: TodoItem, context: Context) {
        val tapResultIntent = Intent(context, MainActivity::class.java)
        tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent: PendingIntent = getActivity(
            context,
            0,
            tapResultIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val notification = createDeadlineNotification(
            context,
            todoItem = todoItem,
            pendingIntent
        )
        val notificationManager = NotificationManagerCompat.from(context)
        notification.let {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@let
            }
            notificationManager.notify(todoItem.id.hashCode(), it)
            Log.d(TAG, "onReceive: notify")
        }
    }

    private fun createDeadlineNotification(
        context: Context,
        todoItem: TodoItem,
        pendingIntent: PendingIntent
    ): Notification {
        Log.d(TAG, "buildNotification: $todoItem")
        val priorityTitle = context.getString(R.string.priority)
        val itemPriority = PriorityMapper.mapToString(todoItem.priority, context)
        val itemDescription = todoItem.text
        return NotificationCompat.Builder(context, NotificationConstants.DEADLINE_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.upcoming_deadline))
            .setContentText(
                context.getString(
                    R.string.deadline_notification_form,
                    priorityTitle,
                    itemPriority,
                    itemDescription
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
    }

    companion object {
        const val TAG = "AlarmReceiver"
    }
}
