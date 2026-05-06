package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
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
import com.tienda.rutadelivery.ui.theme.Cyan
import com.tienda.rutadelivery.ui.theme.Navy
import com.tienda.rutadelivery.ui.theme.NavyIndicator

@Composable
fun MapScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToRuta: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onVerOrden: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Navy) {
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToMap,
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.White) },
                    label = { Text("MAPA", color = Color.White, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = NavyIndicator
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToRuta,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Routes", tint = Color.Gray) },
                    label = { Text("RUTAS", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = NavyIndicator
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToPerfil,
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Gray) },
                    label = { Text("PERFIL", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = NavyIndicator
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Fondo simulando mapa
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Cyan)
            )

            // TopBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF0D1B3E),
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = "KINETIC ARCHITECT",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0D1B3E)
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF0D1B3E), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Barra de búsqueda
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 70.dp)
                    .background(Color.White, RoundedCornerShape(30.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search destination or code", color = Color.Gray, fontSize = 14.sp)
                }
            }

            // Marcadores simulados
            Box(
                modifier = Modifier
                    .padding(start = 80.dp, top = 280.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text(
                        text = "CURRENT POS",
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Box(modifier = Modifier.padding(start = 220.dp, top = 240.dp)) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Miraflores", fontSize = 10.sp, color = Color(0xFF0D1B3E))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, RoundedCornerShape(4.dp))
                        )
                    }
                }
            }

            Box(modifier = Modifier.padding(start = 60.dp, top = 340.dp)) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "San Borja Hub",
                        fontSize = 10.sp,
                        color = Color(0xFF0D1B3E),
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            // Card inferior
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF0D1B3E),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "5 Pedidos listos",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Text(
                                text = "Optimización de ruta lista para el reparto de hoy",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onVerOrden,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF0F0F0)
                            )
                        ) {
                            Text(
                                text = "VER COLA",
                                color = Color(0xFF0D1B3E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Button(
                            onClick = onNavigateToRuta,
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B1A1A)
                            )
                        ) {
                            Text(
                                text = "INICIAR RUTA →",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme { MapScreen() }
}