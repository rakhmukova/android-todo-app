package com.example.todoapp.data.remote

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ErrorConverterCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)

        return object : CallAdapter<Any, Call<Any>> {
            override fun responseType(): Type {
                return responseType
            }

            override fun adapt(call: Call<Any>): Call<Any> {
                return ErrorHandlingCall(call)
            }
        }
    }

    private inner class ErrorHandlingCall<T>(private val delegate: Call<T>) : Call<T> {
        override fun enqueue(callback: Callback<T>) {
            delegate.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful && response.code()
                        in HttpStatusCodes.SUCCESS_RANGE_START..HttpStatusCodes.SUCCESS_RANGE_END
                    ) {
                        callback.onResponse(call, response)
                    } else {
                        callback.onFailure(call, convertToApiException(HttpException(response)))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onFailure(call, convertToApiException(t))
                }
            })
        }

        override fun isExecuted(): Boolean {
            return delegate.isExecuted
        }

        override fun clone(): Call<T> {
            return ErrorHandlingCall(delegate.clone())
        }

        override fun isCanceled(): Boolean {
            return delegate.isCanceled
        }

        override fun cancel() {
            delegate.cancel()
        }

        override fun execute(): Response<T> {
            return delegate.execute()
        }

        override fun request(): Request {
            return delegate.request()
        }

        override fun timeout(): Timeout {
            return delegate.timeout()
        }
    }

    private fun convertToApiException(throwable: Throwable): ApiException {
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
