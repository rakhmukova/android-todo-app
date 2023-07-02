package com.example.todoapp.data.remote

import com.example.todoapp.data.remote.interceptors.AuthInterceptor
import com.example.todoapp.data.remote.interceptors.RetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider {
    companion object {
        fun create(): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                // todo: get token from ui
                .addInterceptor(AuthInterceptor("your_token"))
                .addInterceptor(RetryInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://beta.mrdekk.ru/todobackend/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ErrorConverterCallAdapterFactory())
                .build()
        }
    }
}
