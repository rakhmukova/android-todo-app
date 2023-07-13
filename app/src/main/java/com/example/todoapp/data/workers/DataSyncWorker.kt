package com.example.todoapp.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Worker class for data synchronization.
 */
class DataSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: TodoItemRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            repository.syncTodoItems()
            Result.success()
        } catch (e: Throwable) {
            Log.e(TAG, "doWork: ${e.message}", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "DataSynchronizationWorker"
    }
}
