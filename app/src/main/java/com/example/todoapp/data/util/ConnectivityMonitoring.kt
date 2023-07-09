package com.example.todoapp.data.util

import android.net.ConnectivityManager
import android.net.Network
import com.example.todoapp.di.scope.AppScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Utility class that monitors network connectivity.
 */
@AppScope
class ConnectivityMonitoring @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {
    private val _networkStatus = MutableStateFlow(NetworkStatus.AVAILABLE)
    val networkStatus: StateFlow<NetworkStatus>
        get() = _networkStatus

    fun setupNetworkListener() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkStatus.value = NetworkStatus.AVAILABLE
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkStatus.value = NetworkStatus.LOST
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}
