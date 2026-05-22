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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tienda.rutadelivery.AppState
import com.tienda.rutadelivery.Bici
import com.tienda.rutadelivery.Reserva
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AlquilerScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToMapa: () -> Unit = {},
    onNavigateToRutas: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    var bicis by remember { mutableStateOf(AppState.bicisDisponibles.toList()) }
    var biciSeleccionada by remember { mutableStateOf<Bici?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showExitoDialog by remember { mutableStateOf(false) }
    var filtro by remember { mutableStateOf("Todas") }

    val estaciones = listOf("Todas", "Estación Miraflores", "Estación San Isidro", "Estación Barranco")

    val biciFiltradas = if (filtro == "Todas") bicis
    else bicis.filter { it.estacion == filtro }


    biciSeleccionada?.let { bici ->
        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = {
                    showConfirmDialog = false
                    biciSeleccionada = null
                },
                title = {
                    Text(
                        text = "¿Reservar bici?",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("🚲 ${bici.modelo}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text("📍 ${bici.estacion}", fontSize = 13.sp, color = Color.Gray)
                        Text("⏱ Reserva por 30 minutos", fontSize = 13.sp, color = Color.Gray)
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                            val reserva = Reserva(
                                id = AppState.contadorReservas++,
                                biciId = bici.id,
                                nombreBici = bici.modelo,
                                estacion = bici.estacion,
                                hora = hora
                            )
                            AppState.reservasActivas.add(reserva)
                            AppState.bicisDisponibles.find { it.id == bici.id }?.disponible = false
                            bicis = AppState.bicisDisponibles.toList()
                            showConfirmDialog = false
                            biciSeleccionada = null
                            showExitoDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = {
                        showConfirmDialog = false
                        biciSeleccionada = null
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }


    if (showExitoDialog) {
        AlertDialog(
            onDismissRequest = { showExitoDialog = false },
            title = {
                Text(
                    text = "¡Reserva exitosa! 🎉",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Text("Tu bici está reservada. Tienes 30 minutos para recogerla.")
            },
            confirmButton = {
                Button(
                    onClick = { showExitoDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Entendido")
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
                    selected = false,
                    onClick = onNavigateToPerfil,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.Gray) },
                    label = { Text("PERFIL", color = Color.Gray, fontSize = 10.sp) },
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
                    text = "Alquiler de Bicis 🚲",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "${bicis.count { it.disponible }} bicis disponibles ahora",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Card reservas activas
                if (AppState.reservasActivas.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F0FF))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "🎫 Mis Reservas Activas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            AppState.reservasActivas.forEach { reserva ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("🚲 ${reserva.nombreBici}", fontSize = 13.sp)
                                    Text("⏱ ${reserva.hora}", fontSize = 13.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Filtros estaciones
                Text(
                    text = "Filtrar por estación",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    estaciones.forEach { estacion ->
                        FilterChip(
                            selected = filtro == estacion,
                            onClick = { filtro = estacion },
                            label = { Text(estacion, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF0D1B3E),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Bicis disponibles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            }

            items(biciFiltradas.size) { index ->
                val bici = biciFiltradas[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (bici.disponible) Color.White else Color(0xFFF5F5F5)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    if (bici.disponible) Color(0xFF1565C0).copy(alpha = 0.1f)
                                    else Color.Gray.copy(alpha = 0.1f),
                                    RoundedCornerShape(25.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🚲", fontSize = 24.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = bici.modelo,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (bici.disponible) Color(0xFF0D1B3E) else Color.Gray
                            )
                            Text(
                                text = "📍 ${bici.estacion}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (bici.disponible) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (bici.disponible) "● Disponible" else "● Reservada",
                                    fontSize = 11.sp,
                                    color = if (bici.disponible) Color(0xFF2E7D32) else Color(0xFF8B1A1A),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        if (bici.disponible) {
                            Button(
                                onClick = {
                                    biciSeleccionada = bici
                                    showConfirmDialog = true
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF1565C0)
                                )
                            ) {
                                Text("Reservar", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlquilerScreenPreview() {
    MaterialTheme { AlquilerScreen() }
}