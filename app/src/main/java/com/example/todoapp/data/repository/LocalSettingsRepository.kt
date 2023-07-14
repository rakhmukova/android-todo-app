package com.example.todoapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.todoapp.data.util.AppTheme
import com.example.todoapp.di.scope.AppScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@AppScope
class LocalSettingsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val _appTheme = MutableStateFlow(getAppThemeFromSharedPreferences())
    val appTheme: StateFlow<AppTheme>
        get() = _appTheme

    private fun getAppThemeFromSharedPreferences(): AppTheme {
        val themeName = sharedPreferences.getString(APP_THEME_KEY, AppTheme.SYSTEM.name)
        return themeName?.let { AppTheme.valueOf(it) } ?: AppTheme.SYSTEM
    }

    fun setAppTheme(appTheme: AppTheme) {
        _appTheme.value = appTheme
        Log.d(TAG, "setAppTheme: $appTheme")
        sharedPreferences.edit().putString(APP_THEME_KEY, appTheme.name).apply()
    }

    private val _notificationsEnabled = MutableStateFlow(getNotificationsEnabledFromSharedPreferences())
    val notificationsEnabled: StateFlow<Boolean>
        get() = _notificationsEnabled

    private fun getNotificationsEnabledFromSharedPreferences(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATIONS_ENABLED_KEY, false)
    }

    fun setNotificationsEnabled(notificationsEnabled: Boolean) {
        _notificationsEnabled.value = notificationsEnabled
    }

    companion object {
        private const val APP_THEME_KEY = "app_theme"
        private const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
        const val TAG = "LocalSettingsRepository"
    }
}
