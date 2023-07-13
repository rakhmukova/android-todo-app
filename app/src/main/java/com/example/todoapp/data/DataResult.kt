package com.example.todoapp.data

/**
 * Sealed class representing the state of a data operation.
 */
sealed class DataResult<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T?) : DataResult<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : DataResult<T>(data, throwable)
    class Loading<T> : DataResult<T>()
}
