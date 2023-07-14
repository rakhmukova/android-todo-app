package com.example.todoapp.ui.util.theme.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.util.theme.AppTheme
import com.example.todoapp.ui.util.theme.Blue
import com.example.todoapp.ui.util.theme.BlueTranslucent
import com.example.todoapp.ui.util.theme.Gray
import com.example.todoapp.ui.util.theme.GrayLight
import com.example.todoapp.ui.util.theme.Green
import com.example.todoapp.ui.util.theme.Red
import com.example.todoapp.ui.util.theme.White
import com.example.todoapp.ui.util.theme.darkExtendedColors
import com.example.todoapp.ui.util.theme.lightExtendedColors

@Preview
@Composable
fun DarkColorPalettePreview() {
    AppTheme {
        Column {
            val extendedColors = darkExtendedColors

            with(extendedColors) {
                listOf(
                    supportSeparator,
                    supportOverlay,
                    labelPrimary,
                    labelSecondary,
                    labelTertiary,
                    labelDisable,
                    backPrimary,
                    backSecondary,
                    backElevated
                ).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(color)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LightColorPalettePreview() {
    AppTheme {
        Column {
            val extendedColors = lightExtendedColors

            with(extendedColors) {
                listOf(
                    supportSeparator,
                    supportOverlay,
                    labelPrimary,
                    labelSecondary,
                    labelTertiary,
                    labelDisable,
                    backPrimary,
                    backSecondary,
                    backElevated
                ).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(color)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ColorPalettePreview() {
    Column {
        val colors = listOf(
            Red,
            Green,
            Blue,
            BlueTranslucent,
            Gray,
            GrayLight,
            White
        )

        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color)
            )
        }
    }
}

