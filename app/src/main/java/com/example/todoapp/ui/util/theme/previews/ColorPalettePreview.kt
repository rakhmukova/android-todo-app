package com.example.todoapp.ui.util.theme.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.util.theme.AppTheme
import com.example.todoapp.ui.util.theme.Blue
import com.example.todoapp.ui.util.theme.BlueTranslucent
import com.example.todoapp.ui.util.theme.ExtendedColors
import com.example.todoapp.ui.util.theme.Gray
import com.example.todoapp.ui.util.theme.GrayLight
import com.example.todoapp.ui.util.theme.Green
import com.example.todoapp.ui.util.theme.Red
import com.example.todoapp.ui.util.theme.White
import com.example.todoapp.ui.util.theme.darkExtendedColors
import com.example.todoapp.ui.util.theme.lightExtendedColors

@Composable
private fun List<Color>.PaletteRow() {
    Row {
        forEach { color ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color)
            )
        }
    }
}

@Composable
private fun CommonPaletteRow() {
    listOf(
        Red,
        Green,
        Blue,
        BlueTranslucent,
        Gray,
        GrayLight,
        White
    ).PaletteRow()
}

@Composable
private fun PalettePreview(colors: ExtendedColors) {
    AppTheme {
        with(colors) {
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Transparent)
            ) {
                listOf(
                    supportSeparator,
                    supportOverlay
                ).PaletteRow()
                listOf(
                    labelPrimary,
                    labelSecondary,
                    labelTertiary,
                    labelDisable
                ).PaletteRow()
                CommonPaletteRow()
                listOf(
                    backPrimary,
                    backSecondary,
                    backElevated
                ).PaletteRow()
            }
        }
    }
}

@Preview
@Composable
fun LightColorPalettePreview() {
    PalettePreview(colors = lightExtendedColors)
}

@Preview
@Composable
fun DarkColorPalettePreview() {
    PalettePreview(colors = darkExtendedColors)
}
