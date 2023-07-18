package com.example.todoapp.ui.util

import android.content.Context
import com.example.todoapp.R
import com.example.todoapp.data.util.AppTheme

object ThemeMapper {
    fun mapToString(theme: AppTheme, context: Context): String {
        return when (theme) {
            AppTheme.LIGHT -> context.getString(R.string.light_theme)
            AppTheme.SYSTEM -> context.getString(R.string.system_theme)
            AppTheme.DARK -> context.getString(R.string.dark_theme)
        }
    }
}
