package com.example.todoapp.data.util

import android.content.SharedPreferences
import android.util.Log
import com.example.todoapp.di.scope.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Class responsible for generating and retrieving a unique device ID for the application.
 */
@AppScope
class DeviceIdManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var deviceId: String = ""
        private set
        get() {
            Log.d(TAG, "getDeviceId: $field")
            return field
        }

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            deviceId = loadDeviceId()
        }
    }

    private fun saveDeviceId(deviceId: String) {
        sharedPreferences.edit().putString(DEVICE_ID_KEY, deviceId).apply()
    }

    private suspend fun loadDeviceId(): String = withContext(Dispatchers.IO) {
        var deviceId = sharedPreferences.getString(DEVICE_ID_KEY, null)
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            saveDeviceId(deviceId)
        }

        return@withContext deviceId
    }

    companion object {
        private const val TAG = "DeviceIdManager"
        private const val DEVICE_ID_KEY = "DEVICE_ID"
    }
}
