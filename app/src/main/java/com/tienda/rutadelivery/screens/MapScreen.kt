package com.tienda.rutadelivery.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.tienda.rutadelivery.AppState
import com.tienda.rutadelivery.Punto
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToRutas: () -> Unit = {},
    onNavigateToAlquiler: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    val context = LocalContext.current
    var puntos by remember { mutableStateOf(AppState.puntosGuardados.toList()) }
    var showAddPuntoDialog by remember { mutableStateOf(false) }
    var nuevoPuntoNombre by remember { mutableStateOf("") }
    var nuevoPuntoDireccion by remember { mutableStateOf("") }


    if (showAddPuntoDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddPuntoDialog = false
                nuevoPuntoNombre = ""
                nuevoPuntoDireccion = ""
            },
            title = {
                Text(
                    text = "Nuevo Punto",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nuevoPuntoNombre,
                        onValueChange = { nuevoPuntoNombre = it },
                        label = { Text("Nombre") },
                        placeholder = { Text("Ej: Casa, Trabajo...") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevoPuntoDireccion,
                        onValueChange = { nuevoPuntoDireccion = it },
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
                        if (nuevoPuntoNombre.isNotEmpty() && nuevoPuntoDireccion.isNotEmpty()) {
                            val nuevoPunto = Punto(
                                id = AppState.contadorPuntos++,
                                nombre = nuevoPuntoNombre,
                                direccion = nuevoPuntoDireccion,
                                lat = -12.1219,
                                lon = -77.0290
                            )
                            AppState.puntosGuardados.add(nuevoPunto)
                            puntos = AppState.puntosGuardados.toList()
                            showAddPuntoDialog = false
                            nuevoPuntoNombre = ""
                            nuevoPuntoDireccion = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showAddPuntoDialog = false
                    nuevoPuntoNombre = ""
                    nuevoPuntoDireccion = ""
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddPuntoDialog = true },
                containerColor = Color(0xFF0D1B3E)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar punto", tint = Color.White)
            }
        },
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
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Mapa", tint = Color.White) },
                    label = { Text("MAPA", color = Color.White, fontSize = 10.sp) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Mapa 🗺️",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                    Text(
                        text = "${puntos.size} puntos guardados",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }


            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                factory = { ctx ->
                    Configuration.getInstance().load(
                        ctx,
                        ctx.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
                    )
                    Configuration.getInstance().userAgentValue = ctx.packageName

                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(13.0)
                        controller.setCenter(GeoPoint(-12.1219, -77.0290))
                    }
                },
                update = { mapView ->
                    mapView.overlays.clear()
                    puntos.forEach { punto ->
                        val marker = Marker(mapView)
                        marker.position = GeoPoint(punto.lat, punto.lon)
                        marker.title = punto.nombre
                        marker.snippet = punto.direccion
                        mapView.overlays.add(marker)
                    }
                    mapView.invalidate()
                }
            )


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                item {
                    Text(
                        text = "Mis Puntos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                }
                items(puntos.size) { index ->
                    val punto = puntos[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("📍", fontSize = 20.sp)
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = punto.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0D1B3E)
                                )
                                Text(
                                    text = punto.direccion,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MaterialTheme { MapScreen() }
}