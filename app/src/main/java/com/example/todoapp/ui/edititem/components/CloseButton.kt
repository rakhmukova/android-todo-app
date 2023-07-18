package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun CloseButton(onClose: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = ExtendedTheme.colors.labelPrimary
        ),
        shape = MaterialTheme.shapes.small,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
        content = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.close),
                contentDescription = stringResource(R.string.close_task)
            )
        },
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        onClick = onClose,
    )
}

@Preview
@Composable
fun CloseButtonPreview() {
    CloseButton(onClose = {})
}
