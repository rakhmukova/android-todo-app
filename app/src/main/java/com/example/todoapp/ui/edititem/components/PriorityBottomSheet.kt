package com.example.todoapp.ui.edititem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.ui.util.PriorityMapper

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityBottomSheet(sheetState: ModalBottomSheetState, onPriorityChange: (Priority) -> Unit) {
    // todo: add animation
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.priority),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.padding(12.dp))
                Priority.values().forEach { priority ->
                    Text(
                        // map to string resource :)
                        text = PriorityMapper.mapToString(priority, context),
                        modifier = Modifier
                            .clickable {
                                onPriorityChange(priority)
                            }
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    ) {
        // Screen content
    }
}
