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
import com.tienda.rutadelivery.model.Parada
import com.tienda.rutadelivery.ui.theme.Navy
import com.tienda.rutadelivery.ui.theme.NavyIndicator
import androidx.compose.foundation.lazy.items
import com.tienda.rutadelivery.ui.theme.Cyan

@Composable
fun RutaScreen(
    onNavigateToMap: () -> Unit = {},
    onNavigateToRuta: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Navy) {
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToMap,
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.Gray) },
                    label = { Text("MAPA", color = Color.Gray, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = NavyIndicator
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = onNavigateToRuta,
                    icon = { Icon(Icons.Default.Search, contentDescription = "Routes", tint = Color.White) },
                    label = { Text("RUTAS", color = Color.White, fontSize = 10.sp) },
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
    )  { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Mis Rutas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Text(
                    text = "Rutas optimizadas para hoy",
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
                            Text("24.5 km", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Distancia", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(
                            modifier = Modifier
                                .height(40.dp)
                                .width(1.dp),
                            color = Color.Gray
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("1h 45m", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Tiempo est.", fontSize = 11.sp, color = Color.Gray)
                        }
                        Divider(
                            modifier = Modifier
                                .height(40.dp)
                                .width(1.dp),
                            color = Color.Gray
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("5", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Paradas", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Paradas de hoy",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            }

            // Paradas
            val paradas = listOf(
                Parada("1", "Carlos Rodriguez", "Av. Larco 456, Miraflores", "08:00 AM"),
                Parada("2", "Elena Valdivia", "Calle Libertad 123, San Isidro", "08:10 AM"),
                Parada("3", "Mario Vargas", "Av. Javier Prado 210, Surco", "08:20 AM"),
                Parada("4", "Sofia Mendez", "Calle Alcantores 322, Miraflores", "08:30 AM"),
                Parada("5", "Pedro Soria", "Av. Brasil 1450, Pueblo Libre", "08:40 AM"),
                Parada("6", "Lucia Fernandez", "Av. Arequipa 789, Lince", "08:50 AM"),
                Parada("7", "Jorge Castillo", "Calle Los Pinos 456, San Borja", "09:00 AM"),
                Parada("8", "Andrea Ruiz", "Av. Primavera 1234, Surco", "09:10 AM"),
                Parada("9", "Luis Gutierrez", "Av. La Marina 2300, San Miguel", "09:20 AM"),
                Parada("10", "Patricia Salazar", "Jr. de la Union 567, Cercado de Lima", "09:30 AM"),
                Parada("11", "Diego Ramos", "Av. Universitaria 1500, Los Olivos", "09:40 AM"),
                Parada("12", "Carmen Torres", "Calle Los Olivos 321, Independencia", "09:50 AM"),
                Parada("13", "Fernando Vega", "Av. Benavides 890, Miraflores", "10:00 AM"),
                Parada("14", "Rosa Chavez", "Calle Las Flores 654, Barranco", "10:10 AM"),
                Parada("15", "Miguel Angel Perez", "Av. Faucett 1200, Callao", "10:20 AM")
            )

            items(paradas) { parada ->
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
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFF0D1B3E), RoundedCornerShape(18.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = parada.id,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = parada.nombreUsuario,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Navy
                            )
                            Text(
                                text = parada.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = parada.horaLlegada,
                                fontSize = 12.sp,
                                color = Cyan
                            )
                        }

                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Navy,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onNavigateToMap,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1A1A))
                ) {
                    Text(
                        text = "INICIAR RUTA →",
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