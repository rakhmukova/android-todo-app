package com.example.todoapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSynchronizationWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: TodoItemRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.syncTodoItems()
            Result.success()
        } catch (e: Throwable) {
            Result.failure()
        }
    }
}
