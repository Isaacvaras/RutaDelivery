package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

data class ParadaRuta(
    val numero: Int,
    val nombre: String,
    val direccion: String,
    val distancia: String,
    val tiempo: String,
    val tipo: String
)

@Composable
fun RutaScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToUbicaciones: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    val paradas = listOf(
        ParadaRuta(1, "CityBike Miraflores", "Av. Larco 456, Miraflores", "0.0 km", "Inicio", "🚲"),
        ParadaRuta(2, "Ciclovía Miraflores", "Malecón Cisneros, Miraflores", "1.2 km", "8 min", "🛣️"),
        ParadaRuta(3, "CityBike Barranco", "Av. Grau 150, Barranco", "2.8 km", "15 min", "🚲"),
        ParadaRuta(4, "CityBike San Isidro", "Calle Libertad 123, San Isidro", "4.5 km", "22 min", "🚲"),
        ParadaRuta(5, "Taller Miraflores", "Av. Benavides 890, Miraflores", "6.1 km", "30 min", "🔧"),
        ParadaRuta(6, "CityBike Pueblo Libre", "Av. Brasil 1450, Pueblo Libre", "8.3 km", "42 min", "🚲"),
        ParadaRuta(7, "Taller Lima Centro", "Jr. Cusco 340, Lima", "10.2 km", "51 min", "🔧"),
        ParadaRuta(8, "Ciclovía Surco", "Av. Javier Prado 210, Surco", "12.7 km", "64 min", "🛣️")
    )

    var paradaActual by remember { mutableStateOf(1) }

    Scaffold(
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
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Ruta Óptima 🚲",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "Recorrido sugerido por menor distancia",
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))


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
                            Text("12.7 km", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Distancia total", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("~64 min", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Tiempo est.", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("8", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Paradas", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))



                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Paradas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            }

            items(paradas.size) { index ->
                val parada = paradas[index]
                val completada = parada.numero < paradaActual
                val actual = parada.numero == paradaActual

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            actual -> Color(0xFFE8F0FF)
                            completada -> Color(0xFFF5F5F5)
                            else -> Color.White
                        }
                    ),
                    elevation = CardDefaults.cardElevation(if (actual) 4.dp else 2.dp)
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
                                    when {
                                        completada -> Color(0xFF2E7D32)
                                        actual -> Color(0xFF1565C0)
                                        else -> Color(0xFF0D1B3E)
                                    },
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (completada) "✓" else "${parada.numero}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(parada.tipo, fontSize = 16.sp)
                                Text(
                                    text = parada.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (completada) Color.Gray else Color(0xFF0D1B3E)
                                )
                            }
                            Text(
                                text = parada.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "📍 ${parada.distancia}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1565C0)
                                )
                                Text(
                                    text = "⏱ ${parada.tiempo}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF1565C0)
                                )
                            }
                        }

                        if (actual) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF1565C0), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "ACTUAL",
                                    fontSize = 9.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { if (paradaActual > 1) paradaActual-- },
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("← Anterior", fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
                    }
                    Button(
                        onClick = { if (paradaActual <= paradas.size) paradaActual++ },
                        modifier = Modifier.weight(1f).height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                    ) {
                        Text("Siguiente →", fontWeight = FontWeight.Bold)
                    }
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