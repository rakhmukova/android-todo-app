package com.example.todoapp.data.workers

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todoapp.di.component.AppScope
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Class providing setup for background workers in the application.
 */
@AppScope
class WorkerProvider @Inject constructor(
    private val workManager: WorkManager
) {
    fun setupWorkers() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dataSyncRequest = PeriodicWorkRequest.Builder(
            DataSynchronizationWorker::class.java,
            REPEAT_INTERVAL,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "dataSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dataSyncRequest
        )
    }

    companion object {
        const val REPEAT_INTERVAL: Long = 8
    }
}
