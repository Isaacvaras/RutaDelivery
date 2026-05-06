package com.tienda.rutadelivery.ui.components.individual

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero,
    properties: PopupProperties = PopupProperties(focusable = true),
    shape: Shape = MenuDefaults.shape,
    containerColor: Color = MenuDefaults.containerColor,
    tonalElevation: Dp = MenuDefaults.TonalElevation,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
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
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                content = content
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DropdownMenuPreview() {
    val showMenu = remember { mutableStateOf(true) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .offset(x = 16.dp, y = 80.dp)
        ) {

            Button(
                onClick = { showMenu.value = !showMenu.value }
            ) {
                Text("Abrir menú")
            }

            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value = false },
                offset = DpOffset(0.dp, 8.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Opción 1") },
                    onClick = { showMenu.value = false }
                )

                DropdownMenuItem(
                    text = { Text("Opción 2") },
                    onClick = { showMenu.value = false }
                )

                DropdownMenuItem(
                    text = { Text("Opción 3") },
                    onClick = { showMenu.value = false }
                )
            }
        }
    }
}