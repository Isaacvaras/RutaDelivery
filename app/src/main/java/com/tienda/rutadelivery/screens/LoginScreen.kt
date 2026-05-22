package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tienda.rutadelivery.ui.components.FieldLabel
import com.tienda.rutadelivery.ui.components.StyledTextField
import com.tienda.rutadelivery.ui.theme.BgPage
import com.tienda.rutadelivery.ui.theme.Blue
import com.tienda.rutadelivery.ui.theme.HintGray
import com.tienda.rutadelivery.ui.theme.Navy

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var correo          by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPage),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {


                Text(
                    text = "Bienvenido",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Accede a tu panel de control logístico.",
                    fontSize = 13.sp,
                    color = HintGray
                )

                Spacer(Modifier.height(24.dp))

                // Correo
                FieldLabel("CORREO ELECTRÓNICO")
                Spacer(Modifier.height(6.dp))
                StyledTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    placeholder = "example@gmail.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(Modifier.height(16.dp))

                // Contraseña — label y link en la misma fila
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FieldLabel("CONTRASEÑA")
                    TextButton(
                        onClick = {},
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "¿Olvidaste tu contraseña?",
                            fontSize = 11.sp,
                            color = Blue
                        )
                    }
                }
                Spacer(Modifier.height(6.dp))
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

                Spacer(Modifier.height(24.dp))


                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Navy)
                ) {
                    Text(
                        text = "INGRESAR",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 2.sp
                    )
                }

                Spacer(Modifier.height(20.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "  O CONTINUAR CON  ",
                        fontSize = 11.sp,
                        color = HintGray
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿No tienes una cuenta? ",
                        fontSize = 13.sp,
                        color = HintGray
                    )
                    TextButton(
                        onClick = onRegister,
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "Registrarse",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Navy
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme { LoginScreen() }
}