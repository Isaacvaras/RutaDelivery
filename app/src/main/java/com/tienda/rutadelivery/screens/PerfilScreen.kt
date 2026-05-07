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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToUbicaciones: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
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
                    selected = true,
                    onClick = onNavigateToPerfil,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White) },
                    label = { Text("PROFILE", color = Color.White, fontSize = 10.sp) },
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
                // Avatar y nombre
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
                            text = "Isaac Varas",
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

                // Stats
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
                            Text("2", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Rutas", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("10 km", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Recorridos", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.Gray)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("5 dias 🔥", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Racha", fontSize = 11.sp, color = Color.Gray)
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
                Pair("Correo", "isaac.v@citybike.pe"),
                Pair("Teléfono", "+51 123 123 123"),
                Pair("Zona", "Lima - Miraflores"),
                Pair("Bicicleta", "Trek FX3 - Azul"),
                Pair("Miembro desde", "Enero 2026"),
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
                        Text(item.second, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
                    }
                }
            }

            item {
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