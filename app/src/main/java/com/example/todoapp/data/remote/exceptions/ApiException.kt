package com.example.todoapp.data.remote.exceptions

import java.io.IOException

sealed class ApiException(message: String, cause: Throwable? = null) : IOException(message, cause) {
    class NotSynchronizedDataException : ApiException("Not Synchronized Data")
    class UnauthorizedException : ApiException("Unauthorized")
    class ElementNotFoundException : ApiException("Not Found")
    class ServerException : ApiException("Server Error")
    class NetworkException(cause: Throwable) : ApiException("Network Error", cause)
    class UnknownException(cause: Throwable) : ApiException("Unknown Error", cause)
}
