package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.util.ConnectivityMonitoring
import com.example.todoapp.data.workers.WorkerSetupManager
import com.example.todoapp.di.component.AppComponent
import com.example.todoapp.di.component.DaggerAppComponent
import javax.inject.Inject

/**
 * The application class for the TodoApp.
 */
class TodoApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    @Inject
    lateinit var workerSetupManager: WorkerSetupManager

    @Inject
    lateinit var connectivityMonitoring: ConnectivityMonitoring

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        workerSetupManager.setupWorkers()
        connectivityMonitoring.setupNetworkListener()
    }
}
