package com.example.todoapp.di.component

import android.content.Context
import com.example.todoapp.TodoApp
import com.example.todoapp.di.module.DatabaseModule
import com.example.todoapp.di.module.NetworkModule
import com.example.todoapp.di.module.SharedPreferencesModule
import com.example.todoapp.di.module.WorkerModule
import com.example.todoapp.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        WorkerModule::class,
        SharedPreferencesModule::class
    ]
)
interface AppComponent {
    val activityComponent: ActivityComponent
    fun inject(todoApp: TodoApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
