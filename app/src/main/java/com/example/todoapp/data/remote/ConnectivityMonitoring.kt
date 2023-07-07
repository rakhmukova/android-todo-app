package com.example.todoapp.data.remote

import android.net.ConnectivityManager
import android.net.Network
import com.example.todoapp.data.repository.TodoItemRepository
import com.example.todoapp.di.component.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AppScope
class ConnectivityMonitoring @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val todoItemRepository: TodoItemRepository
) {
    fun setupNetworkListener() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                val coroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    todoItemRepository.syncTodoItems()
                }
            }
            // todo: return flow and collect in repo
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}
