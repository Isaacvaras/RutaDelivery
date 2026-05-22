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
import com.tienda.rutadelivery.Punto
import kotlin.math.*

fun calcularDistanciaKm(p1: Punto, p2: Punto): Double {
    val r = 6371.0
    val dLat = Math.toRadians(p2.lat - p1.lat)
    val dLon = Math.toRadians(p2.lon - p1.lon)
    val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(p1.lat)) *
            cos(Math.toRadians(p2.lat)) *
            sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return (r * c * 10).roundToInt() / 10.0
}

fun calcularRutaOptima(puntos: List<Punto>): List<Punto> {
    if (puntos.size <= 2) return puntos
    val visitados = mutableListOf<Punto>()
    val restantes = puntos.toMutableList()
    visitados.add(restantes.removeFirst())
    while (restantes.isNotEmpty()) {
        val actual = visitados.last()
        val siguiente = restantes.minByOrNull { calcularDistanciaKm(actual, it) }!!
        visitados.add(siguiente)
        restantes.remove(siguiente)
    }
    return visitados
}

@Composable
fun RutaScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToMapa: () -> Unit = {},
    onNavigateToAlquiler: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    var puntos by remember { mutableStateOf(AppState.puntosGuardados.toList()) }
    val rutaOptima = remember(puntos) { calcularRutaOptima(puntos) }

    val distanciaTotal = remember(rutaOptima) {
        if (rutaOptima.size < 2) 0.0
        else rutaOptima.zipWithNext().sumOf { (a, b) -> calcularDistanciaKm(a, b) }
    }

    val tiempoMinutos = remember(distanciaTotal) {
        (distanciaTotal / 15.0 * 60).roundToInt()
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
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Search, contentDescription = "Rutas", tint = Color.White) },
                    label = { Text("RUTAS", color = Color.White, fontSize = 10.sp) },
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
                    text = "Ruta Óptima 🛣️",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "Calculada con tus puntos guardados",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Card resumen
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
                                "$distanciaTotal km",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Distancia total", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(
                            modifier = Modifier.height(40.dp).width(1.dp),
                            color = Color.Gray
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "~$tiempoMinutos min",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Tiempo est.", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(
                            modifier = Modifier.height(40.dp).width(1.dp),
                            color = Color.Gray
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "${puntos.size}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Paradas", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (puntos.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📍", fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "No tienes puntos guardados",
                                    color = Color.Gray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Agrega puntos desde el Mapa o el Home",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Orden óptimo de visita",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                }
            }

            items(rutaOptima.size) { index ->
                val punto = rutaOptima[index]
                val distanciaSegmento = if (index < rutaOptima.size - 1)
                    calcularDistanciaKm(punto, rutaOptima[index + 1])
                else 0.0

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (index == 0) Color(0xFFE8F0FF) else Color.White
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
                                .size(40.dp)
                                .background(
                                    if (index == 0) Color(0xFF1565C0)
                                    else Color(0xFF0D1B3E),
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (index == 0) {
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF1565C0), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("INICIO", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = punto.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0D1B3E)
                                )
                            }
                            Text(
                                text = punto.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            if (index < rutaOptima.size - 1) {
                                Text(
                                    text = "↓ $distanciaSegmento km hasta la siguiente parada",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1565C0)
                                )
                            } else {
                                Text(
                                    text = "🏁 Destino final",
                                    fontSize = 11.sp,
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onNavigateToMapa,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text(
                        text = "Ver en el Mapa 🗺️",
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
fun RutaScreenPreview() {
    MaterialTheme { RutaScreen() }
}