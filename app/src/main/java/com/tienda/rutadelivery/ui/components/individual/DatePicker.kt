package com.tienda.rutadelivery.ui.components.individual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero,
    properties: PopupProperties = PopupProperties(focusable = true),
    shape: Shape = MenuDefaults.shape,
    containerColor: Color = MenuDefaults.containerColor,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    colors: DatePickerColors = DatePickerDefaults.colors(),
    title: @Composable (() -> Unit)? = {
        DatePickerDefaults.DatePickerTitle(
            displayMode = state.displayMode,
            modifier = Modifier.padding(
                start = 24.dp,
                end = 12.dp,
                top = 16.dp
            )
        )
    },
    headline: @Composable (() -> Unit)? = {
        DatePickerDefaults.DatePickerHeadline(
            selectedDateMillis = state.selectedDateMillis,
            displayMode = state.displayMode,
            dateFormatter = dateFormatter,
            modifier = Modifier.padding(
                start = 24.dp,
                end = 12.dp,
                bottom = 12.dp
            )
        )
    },
    showModeToggle: Boolean = true,
) {
    if (!expanded) return

    Popup(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Card(
            modifier = modifier.offset(offset.x, offset.y),
            shape = shape,
            border = border,
            elevation = CardDefaults.cardElevation(
                defaultElevation = shadowElevation
            ),
            colors = CardDefaults.cardColors(
                containerColor = containerColor
            )
        ) {
            DatePicker(
                state = state,
                dateFormatter = dateFormatter,
                colors = colors,
                title = title,
                headline = headline,
                showModeToggle = showModeToggle,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DatePickerMenuPreview() {
    val showPicker = remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {

            Button(
                onClick = { showPicker.value = !showPicker.value }
            ) {
                Text("Abrir calendario")
            }

            DatePickerMenu(
                expanded = showPicker.value,
                onDismissRequest = { showPicker.value = false },
                state = datePickerState,
                offset = DpOffset(0.dp, 8.dp)
            )
        }
    }
}