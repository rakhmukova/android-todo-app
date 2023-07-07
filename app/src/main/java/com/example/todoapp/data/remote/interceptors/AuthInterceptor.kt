package com.example.todoapp.data.remote.interceptors

import android.util.Log
import com.example.todoapp.data.remote.exceptions.HttpStatusCodes
import com.example.todoapp.data.remote.model.UserAuthorization
import com.example.todoapp.data.repository.AuthRepository
import com.example.todoapp.di.component.AppScope
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor responsible for authorization.
 */
@AppScope
class AuthInterceptor @Inject constructor(private val authRepository: AuthRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val user = authRepository.userAuth.value
        val originalRequest = chain.request()
        val request = if (user is UserAuthorization.Authorized) {
            buildRequestWithAccessToken(originalRequest, user.accessToken)
        } else {
            originalRequest
        }
        val originalResponse = chain.proceed(request)
        if (originalResponse.code() == HttpStatusCodes.UNAUTHORIZED_ERROR) {
            // todo: implement logic with authorization in auth repository
            Log.d(TAG, "intercept: ")
        }

        return originalResponse
    }

    private fun buildRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
    }

    companion object {
        private const val TAG = "AuthInterceptor"
    }
}
