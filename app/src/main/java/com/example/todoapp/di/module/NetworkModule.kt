package com.example.todoapp.di.module

import com.example.todoapp.data.remote.ErrorConverterCallAdapterFactory
import com.example.todoapp.data.remote.api.TodoApiService
import com.example.todoapp.data.remote.interceptors.AuthInterceptor
import com.example.todoapp.data.remote.interceptors.RetryInterceptor
import com.example.todoapp.di.component.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    }

    @Provides
    @AppScope
    fun provideTodoApiService(retrofit: Retrofit): TodoApiService {
        return retrofit.create(TodoApiService::class.java)
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            // todo: get token from ui
            .addInterceptor(AuthInterceptor("your_token"))
            .addInterceptor(RetryInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NetworkModule.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ErrorConverterCallAdapterFactory())
            .build()
    }
}
