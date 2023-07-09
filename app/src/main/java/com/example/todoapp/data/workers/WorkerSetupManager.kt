package com.example.todoapp.data.workers

import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Class responsible for setting up and configuring workers in the TodoApp.
 */
class WorkerSetupManager @Inject constructor(
    private val context: Context,
    private val workerFactory: DataSyncWorkerFactory
) {
    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(context, config)
    }

    private fun configureDataSyncWork() {
        val dataSyncRequest = PeriodicWorkRequest.Builder(
            DataSyncWorker::class.java,
            REPEAT_INTERVAL,
            TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            dataSyncRequest
        )
    }

    fun setupWorkers() {
        configureWorkManager()
        configureDataSyncWork()
    }

    companion object {
        const val REPEAT_INTERVAL = 8L
        const val WORK_NAME = "dataSyncWork"
    }
}
