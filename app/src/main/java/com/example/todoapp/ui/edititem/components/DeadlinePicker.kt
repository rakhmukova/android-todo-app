package com.example.todoapp.ui.edititem.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.util.DateParser
import com.example.todoapp.ui.util.theme.Blue
import com.example.todoapp.ui.util.theme.ExtendedTheme
import java.util.*

@Composable
fun DeadlinePicker(
    deadline: Date?,
    onDeadlineChange: (Date?) -> Unit
) {
    // todo: refactor
    val context = LocalContext.current
    val datePickerDialog = remember {
        createDatePickerDialog(
            context,
            onSelectedDate = {
                onDeadlineChange(it)
                createTimePickerDialog(context, it, onDeadlineChange).show()
            }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.deadline),
                color = ExtendedTheme.colors.labelPrimary
            )
            Text(
                text = DateParser.parse(deadline),
                modifier = Modifier.clickable {
                    datePickerDialog.show()
                },
                color = Blue
            )
        }
        Switch(
            checked = deadline != null,
            onCheckedChange = { checked ->
                if (checked) {
                    datePickerDialog.show()
                } else {
                    onDeadlineChange(null)
                }
            },
            colors = SwitchDefaults.colors(checkedThumbColor = Blue)
        )
    }
}

private fun createDatePickerDialog(
    context: Context,
    onSelectedDate: (Date) -> Unit
): DatePickerDialog {
    val currentDate = Calendar.getInstance()
    val currentYear = currentDate.get(Calendar.YEAR)
    val currentMonth = currentDate.get(Calendar.MONTH)
    val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
        context,
        // todo: use compose themes
        R.style.DatePickerStyle,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            onSelectedDate(selectedCalendar.time)
        },
        currentYear,
        currentMonth,
        currentDay
    )
}

private fun createTimePickerDialog(
    context: Context,
    deadline: Date,
    onSelectedDate: (Date) -> Unit
): TimePickerDialog {
    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    return TimePickerDialog(
        context,
        // todo: use compose themes
        R.style.TimePickerStyle,
        { _, selectedHour, selectedMinute ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = deadline
            selectedCalendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            selectedCalendar.set(Calendar.MINUTE, selectedMinute)
            onSelectedDate(selectedCalendar.time)
        },
        hour,
        minute,
        false
    )
}

@Preview
@Composable
fun DeadlinePickerPreview() {
    DeadlinePicker(deadline = Date(), onDeadlineChange = {})
}
