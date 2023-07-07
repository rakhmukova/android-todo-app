package com.example.todoapp.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var retryCount = 0

        // todo: react only to network error
        while (!response.isSuccessful && retryCount < MAX_RETRIES) {
            try {
                response.close()
                response = chain.proceed(request)
                retryCount += 1
            } catch (e: IOException) {
                Log.d(TAG, "intercept: $retryCount ${e.message}", e)
            }
        }

        return response
    }

    companion object {
        const val MAX_RETRIES = 3
        const val TAG = "RetryInterceptor"
    }
}
