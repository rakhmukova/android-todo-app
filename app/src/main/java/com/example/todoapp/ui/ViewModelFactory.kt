package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Factory class for creating ViewModels using Dagger's map of ViewModel providers.
 */
class ViewModelFactory @Inject constructor(
    private val viewModelsFactories: Map<
        Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>
        >
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelsFactories.getValue(modelClass as Class<ViewModel>).get() as T
    }
}
