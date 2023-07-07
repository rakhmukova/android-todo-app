package com.example.todoapp.data

import android.content.SharedPreferences
import android.util.Log
import com.example.todoapp.di.component.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AppScope
class DeviceIdManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private var _deviceId : String = ""

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            _deviceId = loadDeviceId()
        }
    }

    private fun saveDeviceId(androidId: String) {
        sharedPreferences.edit().putString(DEVICE_ID_KEY, androidId).apply()
    }

    private suspend fun loadDeviceId(): String = withContext(Dispatchers.IO) {
        var deviceId = sharedPreferences.getString(DEVICE_ID_KEY, null)
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            saveDeviceId(deviceId)
        }

        return@withContext deviceId
    }

    fun getDeviceId(): String {
        Log.d(TAG, "getDeviceId: $_deviceId")
        return _deviceId
    }

    companion object {
        private const val TAG = "DeviceIdManager"
        private const val DEVICE_ID_KEY = "DEVICE_ID"
    }
}
