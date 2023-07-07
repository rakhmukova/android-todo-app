package com.example.todoapp.di.component

import com.example.todoapp.di.module.ViewModelModule
import com.example.todoapp.ui.ViewModelFactory
import com.example.todoapp.ui.main.MainActivity
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ActivityScope

@Scope
annotation class FragmentScope

@ActivityScope
@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {
    val editTodoItemFragmentComponent: EditTodoItemFragmentComponent
    val todoItemsFragmentComponent: TodoItemsFragmentComponent
    val viewModelFactory: ViewModelFactory

    fun inject(mainActivity: MainActivity)
}
