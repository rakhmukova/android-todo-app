package com.example.todoapp.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.todoapp.data.repository.TodoItemRepository
import javax.inject.Inject

/**
 * Factory class for creating instances of DataSyncWorker.
 */
class DataSyncWorkerFactory @Inject constructor(
    private val repository: TodoItemRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return DataSyncWorker(appContext, workerParameters, repository)
    }
}
