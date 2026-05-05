package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

data class Orden(
    val id: String,
    val cliente: String,
    val prioridad: String,
    val direccion: String,
    val urgente: Boolean = false
)

@Composable
fun OrdenScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToRuta: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    val ordenes = listOf(
        Orden("#LOG-10294", "Carlos Rodriguez", "Prioridad Normal", "Av. Larco 456, Miraflores, Lima"),
        Orden("#LOG-10312", "Elena Valdivia", "Prioridad Alta", "Calle Libertad 123, San Isidro, Lima"),
        Orden("#LOG-10405", "Mario Vargas", "Entrega antes de 14:00", "Av. Javier Prado 210, Surco, Lima", urgente = true),
        Orden("#LOG-10550", "Sofia Mendez", "Prioridad Media", "Calle Alcantores 322, Miraflores, Lima"),
        Orden("#LOG-10672", "Pedro Soria", "Prioridad Normal", "Av. Brasil 1450, Pueblo Libre, Lima")
    )

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
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Search, contentDescription = "Orders", tint = Color.White) },
                    label = { Text("ORDERS", color = Color.White, fontSize = 10.sp) },
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
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Menu, contentDescription = null, tint = Color(0xFF0D1B3E))
                    Text(
                        text = "LIMA LOGISTICS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF0D1B3E)
                    )
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF0D1B3E), RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Gestión de Pedidos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Barra búsqueda
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buscar por ID de pedido o cliente...", color = Color.Gray, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Card seleccionados
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("SELECCIONADOS HOY", fontSize = 10.sp, color = Color.Gray, letterSpacing = 1.sp)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("08", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(" /14", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pedidos Pendientes", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0D1B3E))
                    Text("= FILTRAR", fontSize = 12.sp, color = Color(0xFF0D1B3E))
                }
            }

            // Lista de ordenes
            items(ordenes.size) { index ->
                val orden = ordenes[index]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "PEDIDO ID",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                letterSpacing = 1.sp
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (orden.urgente) Color(0xFFCC0000) else Color(0xFFE8F0FF),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (orden.urgente) "URGENTE" else "PENDIENTE",
                                    fontSize = 10.sp,
                                    color = if (orden.urgente) Color.White else Color(0xFF3366CC),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(orden.id, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF0D1B3E))

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(orden.cliente, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF0D1B3E))
                                Text(orden.prioridad, fontSize = 11.sp, color = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(orden.direccion, fontSize = 11.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("ENTREGAR HOY", fontSize = 10.sp, color = Color.Gray, letterSpacing = 1.sp)
                            Switch(
                                checked = index == 0 || index == 3,
                                onCheckedChange = {},
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF0D1B3E)
                                )
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                // Botón confirmar
                Button(
                    onClick = onNavigateToRuta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text(
                        text = "Confirmar selección y optimizar ruta  ↻",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdenScreenPreview() {
    MaterialTheme { OrdenScreen() }
}