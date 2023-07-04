package com.example.todoapp.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var retryCount = 0

        while (!response.isSuccessful && retryCount < 3) {
            try {
                response.close()
                response = chain.proceed(request)
                retryCount += 1
            } catch (e: IOException) {
                Log.d(TAG, "intercept: $retryCount")
            }
        }

        return response
    }

    companion object {
        const val TAG = "RetryInterceptor"
    }
}
