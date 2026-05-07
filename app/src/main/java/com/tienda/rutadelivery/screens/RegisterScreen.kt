package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "¡Registro exitoso! 🎉",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Text("Tu cuenta ha sido creada correctamente. Ya puedes iniciar sesión.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onRegisterSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Ir al Login")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF1F5)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF0D1B3E)
                    )
                }

                Text(
                    text = "Crear Cuenta 🚲",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "Completa tus datos para registrarte.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                // Nombre
                Text(
                    text = "NOMBRE COMPLETO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E),
                    letterSpacing = 1.sp
                )
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        error = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Tu nombre completo", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )


                Text(
                    text = "CORREO ELECTRÓNICO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E),
                    letterSpacing = 1.sp
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        error = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("nombre@correo.com", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )


                Text(
                    text = "CONTRASEÑA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E),
                    letterSpacing = 1.sp
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        error = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Mínimo 6 caracteres", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )


                Text(
                    text = "CONFIRMAR CONTRASEÑA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E),
                    letterSpacing = 1.sp
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        error = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Repite tu contraseña", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp)
                )


                if (error.isNotEmpty()) {
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }


                Button(
                    onClick = {
                        when {
                            nombre.isEmpty() || email.isEmpty() ||
                                    password.isEmpty() || confirmPassword.isEmpty() ->
                                error = "Por favor completa todos los campos"
                            !email.contains("@") ->
                                error = "El correo no es válido"
                            password.length < 6 ->
                                error = "La contraseña debe tener mínimo 6 caracteres"
                            password != confirmPassword ->
                                error = "Las contraseñas no coinciden"
                            else -> showDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D1B3E)
                    )
                ) {
                    Text(
                        text = "REGISTRARSE",
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta? ",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "Iniciar sesión",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D1B3E)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme { RegisterScreen() }
}