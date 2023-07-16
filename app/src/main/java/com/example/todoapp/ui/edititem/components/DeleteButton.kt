package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.theme.GrayLight
import com.example.todoapp.ui.util.theme.Red

@Composable
fun DeleteButton(onDelete: () -> Unit, enabled: Boolean = true) {
    Button(
        modifier = Modifier.padding(8.dp),
        content = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.delete),
                contentDescription = stringResource(R.string.close_task)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = stringResource(R.string.delete_task))
        },
        onClick = onDelete,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Red,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = GrayLight
        ),
        shape = MaterialTheme.shapes.small,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
        enabled = enabled
    )
}

@Preview
@Composable
fun EnabledDeleteButtonPreview() {
    DeleteButton(onDelete = {}, enabled = true)
}

@Preview
@Composable
fun DisabledDeleteButtonPreview() {
    DeleteButton(onDelete = {}, enabled = false)
}
