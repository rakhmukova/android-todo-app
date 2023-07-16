package com.example.todoapp.ui.settings.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.util.AppTheme
import com.example.todoapp.ui.util.ThemeMapper
import com.example.todoapp.ui.util.components.BodyText
import com.example.todoapp.ui.util.theme.ExtendedTheme

@Composable
fun ThemePicker(appTheme: AppTheme, onThemeChange: (AppTheme) -> Unit) {
    val context = LocalContext.current
    val tabItems = remember { AppTheme.values() }
    val selectedTabIndex = remember { mutableStateOf(tabItems.indexOf(appTheme)) }
    BodyText(
        text = stringResource(id = R.string.theme),
        modifier = Modifier.padding(16.dp),
        color = ExtendedTheme.colors.labelPrimary
    )
    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                height = 1.dp,
                color = ExtendedTheme.colors.labelPrimary
            )
        },
        backgroundColor = ExtendedTheme.colors.backSecondary,
        modifier = Modifier
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        tabItems.forEachIndexed { index, theme ->
            Tab(
                selected = selectedTabIndex.value == index,
                onClick = {
                    onThemeChange(theme)
                    selectedTabIndex.value = index
                },
                text = { Text(text = ThemeMapper.mapToString(theme, context)) },
                selectedContentColor = ExtendedTheme.colors.labelPrimary,
                unselectedContentColor = ExtendedTheme.colors.labelSecondary,
            )
        }
    }
}
