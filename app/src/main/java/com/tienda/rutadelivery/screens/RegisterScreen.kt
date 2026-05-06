package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tienda.rutadelivery.ui.components.FieldLabel
import com.tienda.rutadelivery.ui.components.StyledTextField
import com.tienda.rutadelivery.ui.components.individual.DatePickerMenu
import com.tienda.rutadelivery.ui.theme.BgPage
import com.tienda.rutadelivery.ui.theme.HintGray
import com.tienda.rutadelivery.ui.theme.Navy
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onRegister: (nombre: String, correo: String, dni: String, password: String) -> Unit = { _, _, _, _ -> }
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPw by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPwVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val fechaNacimiento by remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let { millis ->
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
            } ?: ""
        }
    }
    val passwordsMatch by remember {
        derivedStateOf { confirmPw.isEmpty() || password == confirmPw }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPage),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Navy
                    )
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Crear Cuenta",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
                Text(
                    text = "Completa tus datos para registrarte.",
                    fontSize = 13.sp,
                    color = HintGray
                )

                Spacer(Modifier.height(12.dp))

                //DNI
                FieldLabel("DNI")
                Spacer(Modifier.height(4.dp))
                StyledTextField(
                    value = dni,
                    onValueChange = { if (it.length <= 8) dni = it.filter(Char::isDigit) },
                    placeholder = "72773130",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(Modifier.height(8.dp))

                // Fecha de nacimiento
                FieldLabel("FECHA DE NACIMIENTO")
                Spacer(Modifier.height(4.dp))
                Box {
                    StyledTextField(
                        value = fechaNacimiento,
                        onValueChange = {},
                        placeholder = "DD/MM/AAAA",
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Seleccionar fecha",
                                    tint = Navy
                                )
                            }
                        }
                    )
                    DatePickerMenu(
                        expanded = showDatePicker,
                        onDismissRequest = { showDatePicker = false },
                        state = datePickerState,
                        offset = DpOffset(0.dp, 4.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Nombre completo
                FieldLabel("NOMBRE COMPLETO")
                Spacer(Modifier.height(4.dp))
                StyledTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Juan Pérez García",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(Modifier.height(8.dp))

                // Correo electrónico
                FieldLabel("CORREO ELECTRÓNICO")
                Spacer(Modifier.height(4.dp))
                StyledTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    placeholder = "example@gmail.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(Modifier.height(8.dp))

                // Contraseña
                FieldLabel("CONTRASEÑA")
                Spacer(Modifier.height(4.dp))
                StyledTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Mínimo 8 caracteres",
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (passwordVisible)
                                    "Ocultar contraseña" else "Mostrar contraseña",
                                tint = HintGray
                            )
                        }
                    }
                )

                Spacer(Modifier.height(8.dp))

                // Confirmar contraseña
                FieldLabel("CONFIRMAR CONTRASEÑA")
                Spacer(Modifier.height(4.dp))
                StyledTextField(
                    value = confirmPw,
                    onValueChange = { confirmPw = it },
                    placeholder = "Repite tu contraseña",
                    visualTransformation = if (confirmPwVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = !passwordsMatch,
                    trailingIcon = {
                        IconButton(onClick = { confirmPwVisible = !confirmPwVisible }) {
                            Icon(
                                imageVector = if (confirmPwVisible)
                                    Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (confirmPwVisible)
                                    "Ocultar contraseña" else "Mostrar contraseña",
                                tint = HintGray
                            )
                        }
                    }
                )

                if (!passwordsMatch) {
                    Text(
                        text = "Las contraseñas no coinciden",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Botón Registrarse
                val formValid = nombre.isNotBlank()
                        && correo.isNotBlank()
                        && dni.isNotBlank()
                        && password.isNotBlank()
                        && passwordsMatch
                        && confirmPw.isNotBlank()

                Button(
                    onClick = { onRegister(nombre, correo, dni, password) },
                    enabled = formValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor         = Navy,
                        disabledContainerColor = Navy.copy(alpha = 0.4f)
                    )
                ) {
                    Text(
                        text = "REGISTRARSE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Volver al login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta? ",
                        fontSize = 13.sp,
                        color = HintGray
                    )
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Register - Normal")
@Composable
fun RegisterScreenPreview() {
    MaterialTheme { RegisterScreen() }
}
/*
@Preview(showBackground = true, widthDp = 360, heightDp = 640, name = "Register - Small")
@Composable
fun RegisterScreenSmallPreview() {
    MaterialTheme { RegisterScreen() }
}
 */