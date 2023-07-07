package com.example.todoapp.data.remote.exceptions

import retrofit2.HttpException
import java.io.IOException

object ExceptionConverter {
    fun toApiException(throwable: Throwable): ApiException {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    HttpStatusCodes.NOT_SYNCHRONIZED_ERROR -> ApiException.NotSynchronizedDataException()
                    HttpStatusCodes.UNAUTHORIZED_ERROR -> ApiException.UnauthorizedException()
                    HttpStatusCodes.ELEMENT_NOT_FOUND_ERROR -> ApiException.ElementNotFoundException()
                    in HttpStatusCodes.SERVER_ERROR_RANGE_START..HttpStatusCodes.SERVER_ERROR_RANGE_END
                    -> ApiException.ServerException()
                    else -> ApiException.NetworkException(throwable)
                }
            }
            is IOException -> ApiException.NetworkException(throwable)
            else -> ApiException.UnknownException(throwable)
        }
    }
}
