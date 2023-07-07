package com.example.todoapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.example.todoapp.data.remote.api.TodoApiService
import com.example.todoapp.data.remote.exceptions.ErrorConverterCallAdapterFactory
import com.example.todoapp.data.remote.interceptors.AuthInterceptor
import com.example.todoapp.data.remote.interceptors.RetryInterceptor
import com.example.todoapp.di.scope.AppScope
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
        private const val CONNECT_TIMEOUT: Long = 120
        private const val READ_TIMEOUT: Long = 120
        private const val WRITE_TIMEOUT: Long = 90
    }

    @Provides
    @AppScope
    fun provideTodoApiService(retrofit: Retrofit): TodoApiService {
        return retrofit.create(TodoApiService::class.java)
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(RetryInterceptor())
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ErrorConverterCallAdapterFactory())
            .build()
    }

    @Provides
    @AppScope
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
