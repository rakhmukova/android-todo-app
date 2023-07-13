package com.example.todoapp.di.component

import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todolist.TodoItemsFragment
import dagger.Subcomponent

/**
 * Dagger subcomponent for dependencies related to the TodoItemsFragment.
 */
@FragmentScope
@Subcomponent
interface TodoItemsFragmentComponent {
    fun inject(fragment: TodoItemsFragment)
}
