package com.example.todoapp.di.component

import com.example.todoapp.di.module.ViewModelModule
import com.example.todoapp.di.scope.ActivityScope
import com.example.todoapp.ui.ViewModelFactory
import com.example.todoapp.ui.main.MainActivity
import dagger.Subcomponent

/**
 * Dagger subcomponent for activity-level dependencies.
 */
@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {
    val editTodoItemFragmentComponent: EditTodoItemFragmentComponent
    val todoItemsFragmentComponent: TodoItemsFragmentComponent
    val viewModelFactory: ViewModelFactory

    fun inject(mainActivity: MainActivity)
}
