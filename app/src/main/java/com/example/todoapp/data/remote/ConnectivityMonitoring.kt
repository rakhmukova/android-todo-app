package com.example.todoapp.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.example.todoapp.data.repository.TodoItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ConnectivityMonitoring {
    fun setupNetworkListener(
        context: Context,
        todoItemRepository: TodoItemRepository,
        coroutineScope: CoroutineScope
    ) {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                coroutineScope.launch {
                    todoItemRepository.syncTodoItems()
                }
            }
        }

        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}
