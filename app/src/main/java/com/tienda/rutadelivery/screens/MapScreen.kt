package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

data class Estacion(
    val nombre: String,
    val direccion: String,
    val tipo: String,
    val bicicletas: Int,
    val color: Color
)

@Composable
fun MapScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToUbicaciones: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    val estaciones = listOf(
        Estacion("CityBike Miraflores", "Av. Larco 456", "Estación", 8, Color(0xFF1565C0)),
        Estacion("CityBike San Isidro", "Calle Libertad 123", "Estación", 5, Color(0xFF1565C0)),
        Estacion("Ciclovía Surco", "Av. Javier Prado 210", "Ciclovía", 0, Color(0xFF2E7D32)),
        Estacion("Taller Lima Centro", "Jr. Cusco 340", "Taller", 0, Color(0xFF8B1A1A)),
        Estacion("CityBike Barranco", "Av. Grau 150", "Estación", 12, Color(0xFF1565C0)),
        Estacion("Ciclovía Miraflores", "Malecón Cisneros", "Ciclovía", 0, Color(0xFF2E7D32)),
        Estacion("Taller Miraflores", "Av. Benavides 890", "Taller", 0, Color(0xFF8B1A1A)),
        Estacion("CityBike Pueblo Libre", "Av. Brasil 1450", "Estación", 3, Color(0xFF1565C0))
    )

    val filtros = listOf("Todos", "Estación", "Ciclovía", "Taller")
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    var estacionSeleccionada by remember { mutableStateOf<Estacion?>(null) }

    val estacionesFiltradas = if (filtroSeleccionado == "Todos") estaciones
    else estaciones.filter { it.tipo == filtroSeleccionado }

    // Dialog detalle estación
    estacionSeleccionada?.let { estacion ->
        AlertDialog(
            onDismissRequest = { estacionSeleccionada = null },
            title = {
                Text(
                    text = estacion.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("📍 ${estacion.direccion}", fontSize = 13.sp)
                    Text("🏷️ Tipo: ${estacion.tipo}", fontSize = 13.sp)
                    if (estacion.tipo == "Estación") {
                        Text(
                            "🚲 Bicicletas disponibles: ${estacion.bicicletas}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { estacionSeleccionada = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Cerrar")
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0D1B3E)) {
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToMap,
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.White) },
                    label = { Text("MAP", color = Color.White, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFF1A2E5A))
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToUbicaciones,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Ubicaciones", tint = Color.Gray) },
                    label = { Text("UBICACIONES", color = Color.Gray, fontSize = 10.sp) },
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
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            item {
                // Mapa simulado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(Color(0xFF7EC8C8))
                ) {
                    // Calles simuladas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.White.copy(alpha = 0.4f))
                            .align(Alignment.Center)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(3.dp)
                            .background(Color.White.copy(alpha = 0.4f))
                            .align(Alignment.Center)
                    )

                    // Marcadores estaciones
                    estaciones.forEachIndexed { index, estacion ->
                        val offsetX = (20 + (index * 67) % 280).dp
                        val offsetY = (40 + (index * 43) % 180).dp
                        Box(
                            modifier = Modifier
                                .padding(start = offsetX, top = offsetY)
                                .size(12.dp)
                                .background(estacion.color, RoundedCornerShape(6.dp))
                        )
                    }

                    // Punto usuario pulsante
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFF1565C0).copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .align(Alignment.Center)
                                .background(Color(0xFF1565C0), RoundedCornerShape(7.dp))
                        )
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.Center)
                                .background(Color.White, RoundedCornerShape(3.dp))
                        )
                    }

                    // Label ubicación actual
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B3E))
                    ) {
                        Text(
                            text = "📍 Tu ubicación actual",
                            color = Color.White,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    // Leyenda
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFF1565C0), RoundedCornerShape(4.dp)))
                                Text("Estación", fontSize = 9.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFF2E7D32), RoundedCornerShape(4.dp)))
                                Text("Ciclovía", fontSize = 9.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFF8B1A1A), RoundedCornerShape(4.dp)))
                                Text("Taller", fontSize = 9.sp)
                            }
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Estaciones CityBike",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                    Text(
                        text = "${estacionesFiltradas.size} puntos encontrados",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Chips filtro
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(filtros) { filtro ->
                            FilterChip(
                                selected = filtroSeleccionado == filtro,
                                onClick = { filtroSeleccionado = filtro },
                                label = { Text(filtro, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF0D1B3E),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Lista estaciones
            items(estacionesFiltradas.size) { index ->
                val estacion = estacionesFiltradas[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { estacionSeleccionada = estacion },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(estacion.color.copy(alpha = 0.1f), RoundedCornerShape(22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = when(estacion.tipo) {
                                    "Estación" -> "🚲"
                                    "Ciclovía" -> "🛣️"
                                    else -> "🔧"
                                },
                                fontSize = 20.sp
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = estacion.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Text(
                                text = estacion.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            if (estacion.tipo == "Estación") {
                                Text(
                                    text = "${estacion.bicicletas} bicicletas disponibles",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1565C0),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .background(estacion.color, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = estacion.tipo,
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
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
fun MapScreenPreview() {
    MaterialTheme { MapScreen() }
}