package com.example.todoapp.ui.util.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.example.todoapp.R

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val extendedColors = if (isSystemInDarkTheme()) darkExtendedColors else lightExtendedColors
    val colors = if (isSystemInDarkTheme()) {
        darkColors(
            primary = Color(R.color.back_primary),
            onPrimary = Color(R.color.label_primary),
            secondary = Color(R.color.back_secondary),
            onSecondary = Color(R.color.label_secondary)
        )
    } else {
        lightColors(
            primary = Color(R.color.back_primary),
            onPrimary = Color(R.color.label_primary),
            secondary = Color(R.color.back_secondary),
            onSecondary = Color(R.color.label_secondary)
        )
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            content = content
        )
    }
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
