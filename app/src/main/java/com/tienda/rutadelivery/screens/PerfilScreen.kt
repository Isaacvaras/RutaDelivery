package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import com.tienda.rutadelivery.AppState

@Composable
fun PerfilScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToMapa: () -> Unit = {},
    onNavigateToRutas: () -> Unit = {},
    onNavigateToAlquiler: () -> Unit = {},
    onLogout: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf(AppState.usuarioActual.nombre) }
    var email by remember { mutableStateOf(AppState.usuarioActual.email) }
    var telefono by remember { mutableStateOf(AppState.usuarioActual.telefono) }

    var editNombre by remember { mutableStateOf("") }
    var editEmail by remember { mutableStateOf("") }
    var editTelefono by remember { mutableStateOf("") }

    var passwordActual by remember { mutableStateOf("") }
    var passwordNueva by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }


    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text(
                    text = "Editar Perfil",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = editNombre,
                        onValueChange = { editNombre = it },
                        label = { Text("Nombre") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Correo") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editTelefono,
                        onValueChange = { editTelefono = it },
                        label = { Text("Teléfono") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editNombre.isNotEmpty()) {
                            AppState.usuarioActual.nombre = editNombre
                            nombre = editNombre
                        }
                        if (editEmail.isNotEmpty()) {
                            AppState.usuarioActual.email = editEmail
                            email = editEmail
                        }
                        if (editTelefono.isNotEmpty()) {
                            AppState.usuarioActual.telefono = editTelefono
                            telefono = editTelefono
                        }
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }


    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = {
                showPasswordDialog = false
                passwordActual = ""
                passwordNueva = ""
                passwordConfirm = ""
                passwordError = ""
            },
            title = {
                Text(
                    text = "Cambiar Contraseña",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = passwordActual,
                        onValueChange = {
                            passwordActual = it
                            passwordError = ""
                        },
                        label = { Text("Contraseña actual") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = passwordNueva,
                        onValueChange = {
                            passwordNueva = it
                            passwordError = ""
                        },
                        label = { Text("Nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = passwordConfirm,
                        onValueChange = {
                            passwordConfirm = it
                            passwordError = ""
                        },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (passwordError.isNotEmpty()) {
                        Text(
                            text = passwordError,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        when {
                            passwordActual != AppState.usuarioActual.password ->
                                passwordError = "La contraseña actual no es correcta"
                            passwordNueva.length < 6 ->
                                passwordError = "La nueva contraseña debe tener mínimo 6 caracteres"
                            passwordNueva != passwordConfirm ->
                                passwordError = "Las contraseñas no coinciden"
                            else -> {
                                AppState.usuarioActual.password = passwordNueva
                                showPasswordDialog = false
                                passwordActual = ""
                                passwordNueva = ""
                                passwordConfirm = ""
                                passwordError = ""
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showPasswordDialog = false
                    passwordActual = ""
                    passwordNueva = ""
                    passwordConfirm = ""
                    passwordError = ""
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0D1B3E)) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToHome,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Gray) },
                    label = { Text("HOME", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMapa,
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Mapa", tint = Color.Gray) },
                    label = { Text("MAPA", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToRutas,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Rutas", tint = Color.Gray) },
                    label = { Text("RUTAS", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White) },
                    label = { Text("PERFIL", color = Color.White, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .background(Color(0xFF1565C0), RoundedCornerShape(45.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🚲", fontSize = 40.sp)
                        }
                        Text(
                            text = nombre,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D1B3E)
                        )
                        Text(
                            text = "Ciclista Urbano",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "● Activo",
                                fontSize = 12.sp,
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${AppState.rutasGuardadas.size}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Rutas", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${AppState.puntosGuardados.size}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Puntos", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${AppState.reservasActivas.size}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Reservas", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Información Personal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            }

            val infoItems = listOf(
                Pair("Correo", email),
                Pair("Teléfono", telefono),
                Pair("Zona", "Lima - Miraflores"),
                Pair("Plan", "Premium 🌟")
            )

            items(infoItems.size) { index ->
                val item = infoItems[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.first, fontSize = 13.sp, color = Color.Gray)
                        Text(
                            item.second,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D1B3E)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))


                Button(
                    onClick = {
                        editNombre = nombre
                        editEmail = email
                        editTelefono = telefono
                        showEditDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text(
                        text = "Editar Perfil ✏️",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))


                OutlinedButton(
                    onClick = { showPasswordDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cambiar Contraseña 🔒",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF0D1B3E)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1A1A))
                ) {
                    Text(
                        text = "CERRAR SESIÓN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    MaterialTheme { PerfilScreen() }
}