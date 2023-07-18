package com.example.todoapp.data.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todoapp.data.DataResult
import com.example.todoapp.data.model.TodoItem
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.data.util.ChangeItemAction
import com.example.todoapp.data.util.DateConverter
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScope
class AlarmScheduler @Inject constructor(
    private val context: Context,
    private val todoItemRepository: TodoItemRepository,
    private val dateConverter: DateConverter
) {
    init {
        setupItemChangeListener()
    }

    private fun setupItemChangeListener() {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            Log.d(TAG, "launched: ")
            todoItemRepository.changeItemState.collect {
                Log.d(TAG, "collect change: $it")
                when (it) {
                    is DataResult.Success -> {
                        val itemAction = it.data
                        if (itemAction != null) {
                            handleAction(itemAction)
                        }
                    } else -> {}
                }
            }
        }
    }

    private suspend fun handleAction(itemAction: ChangeItemAction) {
        when (itemAction) {
            is ChangeItemAction.Add -> {
                val todoItem = todoItemRepository.findById(itemAction.todoItemId) ?: return
                Log.d(TAG, "handleAction: $todoItem")
                scheduleAlarm(todoItem)
            }
            is ChangeItemAction.Update -> {
                // if deadline or done property is changed,
                // possibly cancel and set new alarm
            }
            is ChangeItemAction.Delete -> {
                // cancel alarm
            }
        }
    }

    private fun scheduleAlarm(todoItem: TodoItem) {
        // todo: if item is not checked
        val deadline = todoItem.deadline ?: return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(NotificationConstants.TODO_ITEM_ID_KEY, todoItem.id)
        val itemHashCode = todoItem.id.hashCode()
        // todo: add redirect to a specific fragment in intent
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            itemHashCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        val basicPendingIntent = PendingIntent.getActivity(
            context,
            itemHashCode,
            mainActivityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val clockInfo = AlarmManager.AlarmClockInfo(
            dateConverter.fromDate(deadline),
            basicPendingIntent
        )
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
        Log.d(TAG, "setAlarm: ")
    }

    companion object {
        const val TAG = "NotificationCreator"
    }
}
