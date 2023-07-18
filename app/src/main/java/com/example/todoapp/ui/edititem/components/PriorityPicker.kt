package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.ui.util.PriorityMapper
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun PriorityPicker(priority: Priority, onPriorityClick: (Int) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.priority),
            color = ExtendedTheme.colors.labelPrimary
        )
        ClickableText(
            text = AnnotatedString(PriorityMapper.mapToString(priority, context)),
            onClick = onPriorityClick,
            style = TextStyle(color = ExtendedTheme.colors.labelSecondary)
        )
    }
}

@Preview
@Composable
fun PriorityPickerPreview() {
    PriorityPicker(priority = Priority.HIGH, onPriorityClick = {})
}
