package com.example.todoapp.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkerProvider {
    companion object {
        fun setupWorkers(context: Context) {
            val workManager = WorkManager.getInstance(context)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val dataSyncRequest = PeriodicWorkRequest.Builder(
                DataSynchronizationWorker::class.java,
                8, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "dataSyncWork",
                ExistingPeriodicWorkPolicy.KEEP,
                dataSyncRequest
            )
        }
    }
}