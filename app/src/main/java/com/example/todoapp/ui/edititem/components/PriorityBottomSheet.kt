package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.ui.util.PriorityMapper
import com.example.todoapp.ui.util.theme.Gray
import com.example.todoapp.ui.util.theme.Red

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityBottomSheet(
    sheetState: ModalBottomSheetState,
    onPriorityChange: (Priority) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.priority),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Priority.values().forEach { priority ->
                    val rippleColor = if (priority == Priority.HIGH) Red else Gray
                    Text(
                        text = PriorityMapper.mapToString(priority, context),
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    color = rippleColor
                                )
                            ) {
                                onPriorityChange(priority)
                            }
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(vertical = 8.dp, horizontal = 20.dp)
                    )
                }
            }
        },
        content = content
    )
}
