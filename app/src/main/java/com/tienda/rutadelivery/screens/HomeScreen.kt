package com.tienda.rutadelivery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.tienda.rutadelivery.Ruta
import kotlin.math.*

fun distanciaEntre(p1: Punto, p2: Punto): Double {
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

@Composable
fun HomeScreen(
    onNavigateToMapa: () -> Unit = {},
    onNavigateToRutas: () -> Unit = {},
    onNavigateToAlquiler: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    var rutas by remember { mutableStateOf(AppState.rutasGuardadas.toList()) }
    var puntos by remember { mutableStateOf(AppState.puntosGuardados.toList()) }

    var showAddRutaDialog by remember { mutableStateOf(false) }
    var showAddPuntoDialog by remember { mutableStateOf(false) }
    var showDeleteRutaDialog by remember { mutableStateOf<Ruta?>(null) }
    var showDeletePuntoDialog by remember { mutableStateOf<Punto?>(null) }
    var showRutaDetalleDialog by remember { mutableStateOf<Ruta?>(null) }

    var nuevaRutaNombre by remember { mutableStateOf("") }
    var paradasSeleccionadas by remember { mutableStateOf<List<Punto>>(emptyList()) }

    var nuevoPuntoNombre by remember { mutableStateOf("") }
    var nuevoPuntoDireccion by remember { mutableStateOf("") }

    // Dialog crear ruta con paradas
    if (showAddRutaDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddRutaDialog = false
                nuevaRutaNombre = ""
                paradasSeleccionadas = emptyList()
            },
            title = {
                Text(
                    text = "Nueva Ruta",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = nuevaRutaNombre,
                        onValueChange = { nuevaRutaNombre = it },
                        label = { Text("Nombre de la ruta") },
                        placeholder = { Text("Ej: Ruta al trabajo") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Selecciona las paradas:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )

                    if (AppState.puntosGuardados.isEmpty()) {
                        Text(
                            text = "No tienes puntos guardados. Agrega puntos primero.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    } else {
                        AppState.puntosGuardados.forEach { punto ->
                            val seleccionado = paradasSeleccionadas.contains(punto)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        paradasSeleccionadas = if (seleccionado)
                                            paradasSeleccionadas - punto
                                        else
                                            paradasSeleccionadas + punto
                                    }
                                    .background(
                                        if (seleccionado) Color(0xFFE8F0FF)
                                        else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Checkbox(
                                    checked = seleccionado,
                                    onCheckedChange = {
                                        paradasSeleccionadas = if (seleccionado)
                                            paradasSeleccionadas - punto
                                        else
                                            paradasSeleccionadas + punto
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFF1565C0)
                                    )
                                )
                                Column {
                                    Text(
                                        text = punto.nombre,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0D1B3E)
                                    )
                                    Text(
                                        text = punto.direccion,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    if (paradasSeleccionadas.size >= 2) {
                        val distancia = paradasSeleccionadas
                            .zipWithNext()
                            .sumOf { (a, b) -> distanciaEntre(a, b) }
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("📍 ${paradasSeleccionadas.size} paradas", fontSize = 12.sp)
                                Text("·", fontSize = 12.sp, color = Color.Gray)
                                Text("🚲 $distancia km", fontSize = 12.sp, color = Color(0xFF2E7D32))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nuevaRutaNombre.isNotEmpty() && paradasSeleccionadas.isNotEmpty()) {
                            val distancia = if (paradasSeleccionadas.size >= 2)
                                paradasSeleccionadas.zipWithNext()
                                    .sumOf { (a, b) -> distanciaEntre(a, b) }
                            else 0.0

                            val nuevaRuta = Ruta(
                                id = AppState.contadorRutas++,
                                nombre = nuevaRutaNombre,
                                puntos = paradasSeleccionadas,
                                distanciaKm = distancia
                            )
                            AppState.rutasGuardadas.add(nuevaRuta)
                            rutas = AppState.rutasGuardadas.toList()
                            showAddRutaDialog = false
                            nuevaRutaNombre = ""
                            paradasSeleccionadas = emptyList()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E)),
                    enabled = nuevaRutaNombre.isNotEmpty() && paradasSeleccionadas.isNotEmpty()
                ) {
                    Text("Crear Ruta")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showAddRutaDialog = false
                    nuevaRutaNombre = ""
                    paradasSeleccionadas = emptyList()
                }) {
                    Text("Cancelar")
                }
            }
        )
    }


    showRutaDetalleDialog?.let { ruta ->
        AlertDialog(
            onDismissRequest = { showRutaDetalleDialog = null },
            title = {
                Text(
                    text = ruta.nombre,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "📍 ${ruta.puntos.size} paradas · 🚲 ${ruta.distanciaKm} km",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    Divider()
                    ruta.puntos.forEachIndexed { index, punto ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(Color(0xFF0D1B3E), RoundedCornerShape(14.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${index + 1}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    punto.nombre,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D1B3E)
                                )
                                Text(punto.direccion, fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                        if (index < ruta.puntos.size - 1) {
                            Text(
                                "↓ ${distanciaEntre(punto, ruta.puntos[index + 1])} km",
                                fontSize = 11.sp,
                                color = Color(0xFF1565C0),
                                modifier = Modifier.padding(start = 36.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showRutaDetalleDialog = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D1B3E))
                ) {
                    Text("Cerrar")
                }
            }
        )
    }


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


    showDeleteRutaDialog?.let { ruta ->
        AlertDialog(
            onDismissRequest = { showDeleteRutaDialog = null },
            title = {
                Text("¿Eliminar ruta?", fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
            },
            text = { Text("¿Seguro que deseas eliminar \"${ruta.nombre}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        AppState.rutasGuardadas.remove(ruta)
                        rutas = AppState.rutasGuardadas.toList()
                        showDeleteRutaDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1A1A))
                ) { Text("Eliminar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeleteRutaDialog = null }) { Text("Cancelar") }
            }
        )
    }


    showDeletePuntoDialog?.let { punto ->
        AlertDialog(
            onDismissRequest = { showDeletePuntoDialog = null },
            title = {
                Text("¿Eliminar punto?", fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
            },
            text = { Text("¿Seguro que deseas eliminar \"${punto.nombre}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        AppState.puntosGuardados.remove(punto)
                        puntos = AppState.puntosGuardados.toList()
                        showDeletePuntoDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B1A1A))
                ) { Text("Eliminar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDeletePuntoDialog = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0D1B3E)) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
                    label = { Text("HOME", color = Color.White, fontSize = 10.sp) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hola, ${AppState.usuarioActual.nombre.split(" ").first()} 👋",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D1B3E)
                        )
                        Text(
                            text = "¿A dónde pedaleamos hoy?",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF1565C0), RoundedCornerShape(24.dp))
                            .clickable { onNavigateToPerfil() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🚲", fontSize = 22.sp)
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1B3E))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${rutas.size}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Rutas", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("${puntos.size}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Puntos", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "${AppState.bicisDisponibles.count { it.disponible }}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text("Bicis", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Accesos rápidos",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B3E)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Card(
                            modifier = Modifier.width(120.dp).clickable { onNavigateToMapa() },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F0FF))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🗺️", fontSize = 28.sp)
                                Text("Ver Mapa", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier.width(120.dp).clickable { onNavigateToRutas() },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🛣️", fontSize = 28.sp)
                                Text("Ruta Óptima", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier.width(120.dp).clickable { onNavigateToAlquiler() },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🚲", fontSize = 28.sp)
                                Text("Alquilar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B3E))
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis Rutas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                    IconButton(onClick = { showAddRutaDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar ruta", tint = Color(0xFF1565C0))
                    }
                }
            }

            if (rutas.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No tienes rutas guardadas", color = Color.Gray, fontSize = 13.sp)
                        }
                    }
                }
            }

            items(rutas) { ruta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showRutaDetalleDialog = ruta },
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
                                .background(Color(0xFF1565C0).copy(alpha = 0.1f), RoundedCornerShape(22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🛣️", fontSize = 20.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ruta.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Text(
                                text = "${ruta.puntos.size} paradas · ${ruta.distanciaKm} km",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = { showDeleteRutaDialog = ruta }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFF8B1A1A))
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis Puntos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B3E)
                    )
                    IconButton(onClick = { showAddPuntoDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar punto", tint = Color(0xFF1565C0))
                    }
                }
            }

            if (puntos.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No tienes puntos guardados", color = Color.Gray, fontSize = 13.sp)
                        }
                    }
                }
            }

            items(puntos) { punto ->
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
                                .size(44.dp)
                                .background(Color(0xFF2E7D32).copy(alpha = 0.1f), RoundedCornerShape(22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📍", fontSize = 20.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = punto.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0D1B3E)
                            )
                            Text(
                                text = punto.direccion,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        IconButton(onClick = { showDeletePuntoDialog = punto }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFF8B1A1A))
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
fun HomeScreenPreview() {
    MaterialTheme { HomeScreen() }
}