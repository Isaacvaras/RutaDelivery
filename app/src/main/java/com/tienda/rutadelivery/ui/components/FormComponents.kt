package com.tienda.rutadelivery.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tienda.rutadelivery.ui.theme.BgField
import com.tienda.rutadelivery.ui.theme.Navy
import com.tienda.rutadelivery.ui.theme.TextError
import com.tienda.rutadelivery.ui.theme.TextHint
import com.tienda.rutadelivery.ui.theme.TextPrimary

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        letterSpacing = 1.2.sp
    )
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        readOnly = readOnly,
        isError = isError,
        placeholder = {
            Text(
                text = placeholder,
                color = TextHint,
                fontSize = 14.sp
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = BgField,
            focusedContainerColor   = BgField,
            unfocusedBorderColor    = Color.Transparent,
            focusedBorderColor      = Navy.copy(alpha = 0.6f),
            errorBorderColor        = TextError,
            cursorColor             = Navy
        )
    )
}