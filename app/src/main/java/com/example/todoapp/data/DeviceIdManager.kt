package com.example.todoapp.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

object DeviceIdManager {

    private const val DEVICE_ID_KEY = "DEVICE_ID"
    private var _deviceId : String? = null

    private fun saveDeviceId(sharedPreferences: SharedPreferences, androidId: String) {
        sharedPreferences.edit().putString(DEVICE_ID_KEY, androidId).apply()
    }

    fun loadDeviceId(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences("ANDROID_ID_PREFERENCES", Context.MODE_PRIVATE)
        var deviceId = sharedPreferences.getString(DEVICE_ID_KEY, null)
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            saveDeviceId(sharedPreferences, deviceId)
        }

        _deviceId = deviceId
    }

    fun getDeviceId(): String {
        Log.d(TAG, "getDeviceId: $_deviceId")
        return _deviceId?: ""
    }

    private const val TAG = "DeviceIdManager"
}
