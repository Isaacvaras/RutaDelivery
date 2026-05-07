package com.tienda.rutadelivery.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Ubicacion(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val tipo: String
)

@Composable
fun UbicacionesScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToUbicaciones: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    val ubicaciones = remember {
        mutableStateListOf(
            Ubicacion(1, "Casa", "Av. Larco 456, Miraflores", "🏠"),
            Ubicacion(2, "Trabajo", "Calle Libertad 123, San Isidro", "🏢"),
            Ubicacion(3, "Gimnasio", "Av. Benavides 890, Miraflores", "🏋️")
        )
    }

    var ubicacionAEliminar by remember { mutableStateOf<Ubicacion?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevaDireccion by remember { mutableStateOf("") }


    ubicacionAEliminar?.let { ubicacion ->
        AlertDialog(
            onDismissRequest = { ubicacionAEliminar = null },
            title = {
                Text(
                    text = "¿Eliminar ubicación?",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Text("¿Estás seguro que deseas eliminar \"${ubicacion.nombre}\"?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        ubicaciones.remove(ubicacion)
                        ubicacionAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1A1A))
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { ubicacionAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }


    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                nuevoNombre = ""
                nuevaDireccion = ""
            },
            title = {
                Text(
                    text = "Nueva Ubicación",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        label = { Text("Nombre") },
                        placeholder = { Text("Ej: Casa, Trabajo...") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevaDireccion,
                        onValueChange = { nuevaDireccion = it },
                        label = { Text("Dirección") },
                        placeholder = { Text("Av. ejemplo 123") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nuevoNombre.isNotEmpty() && nuevaDireccion.isNotEmpty()) {
                            ubicaciones.add(
                                Ubicacion(
                                    id = ubicaciones.size + 1,
                                    nombre = nuevoNombre,
                                    direccion = nuevaDireccion,
                                    tipo = "📍"
                                )
                            )
                            showAddDialog = false
                            nuevoNombre = ""
                            nuevaDireccion = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showAddDialog = false
                        nuevoNombre = ""
                        nuevaDireccion = ""
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF0D1B3E)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0D1B3E)) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMap,
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.Gray) },
                    label = { Text("MAP", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToUbicaciones,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Ubicaciones", tint = Color.White) },
                    label = { Text("UBICACIONES", color = Color.White, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToPerfil,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Gray) },
                    label = { Text("PROFILE", color = Color.Gray, fontSize = 10.sp) },
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
                Text(
                    text = "Mis Ubicaciones",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "${ubicaciones.size} ubicaciones guardadas",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(ubicaciones) { ubicacion ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = ubicacion.tipo, fontSize = 28.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ubicacion.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Text(
                                text = ubicacion.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(
                            onClick = { ubicacionAEliminar = ubicacion }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color(0xFF8B1A1A)
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UbicacionesScreenPreview() {
    MaterialTheme { UbicacionesScreen() }
}