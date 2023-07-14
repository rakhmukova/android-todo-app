package com.example.todoapp.di.component

import android.content.Context
import com.example.todoapp.TodoApp
import com.example.todoapp.data.notifications.AlarmReceiver
import com.example.todoapp.di.module.DatabaseModule
import com.example.todoapp.di.module.NetworkModule
import com.example.todoapp.di.module.NotificationModule
import com.example.todoapp.di.module.SharedPreferencesModule
import com.example.todoapp.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

/**
 * Dagger component for app-level dependencies.
 */
@AppScope
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        SharedPreferencesModule::class,
        NotificationModule::class
    ]
)
interface AppComponent {
    val activityComponent: ActivityComponent
    fun inject(todoApp: TodoApp)
    fun inject(alarmReceiver: AlarmReceiver)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
