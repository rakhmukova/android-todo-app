package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.util.ConnectivityMonitoring
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.data.workers.WorkerProvider
import com.example.todoapp.di.component.AppComponent
import com.example.todoapp.di.component.DaggerAppComponent
import javax.inject.Inject

class TodoApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    @Inject
    lateinit var todoItemRepository: TodoItemRepository

    @Inject
    lateinit var workerProvider: WorkerProvider

    @Inject
    lateinit var connectivityMonitoring: ConnectivityMonitoring

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        connectivityMonitoring.setupNetworkListener()
        // todo: use factory
        workerProvider.setupWorkers()
    }
}
