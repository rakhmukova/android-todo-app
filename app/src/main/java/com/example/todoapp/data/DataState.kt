package com.example.todoapp.data

/**
 * Sealed class representing the state of a data operation.
 */
sealed class DataState<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T?) : DataState<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : DataState<T>(data, throwable)
    class Loading<T> : DataState<T>()
}
