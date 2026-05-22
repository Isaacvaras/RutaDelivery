package com.tienda.rutadelivery

data class Usuario(
    val id: Int = 1,
    var nombre: String = "",
    var email: String = "",
    var telefono: String = "",
    var password: String = ""
)

data class Punto(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val lat: Double,
    val lon: Double
)

data class Ruta(
    val id: Int,
    val nombre: String,
    val puntos: List<Punto>,
    val distanciaKm: Double
)

data class Bici(
    val id: Int,
    val modelo: String,
    val estacion: String,
    var disponible: Boolean = true
)

data class Reserva(
    val id: Int,
    val biciId: Int,
    val nombreBici: String,
    val estacion: String,
    val hora: String
)

// Estado global de la app en memoria
object AppState {
    var usuarioActual = Usuario(
        nombre = "Isaac Varas",
        email = "isaac.varas@citybike.pe",
        telefono = "+51 987 654 321",
        password = "citybike123"
    )

    val puntosGuardados = mutableListOf(
        Punto(1, "Casa", "Av. Larco 456, Miraflores", -12.1219, -77.0290),
        Punto(2, "Trabajo", "Calle Libertad 123, San Isidro", -12.0975, -77.0353),
        Punto(3, "Gimnasio", "Av. Benavides 890, Miraflores", -12.1340, -77.0150)
    )

    val rutasGuardadas = mutableListOf(
        Ruta(1, "Ruta Miraflores", listOf(
            Punto(1, "Casa", "Av. Larco 456", -12.1219, -77.0290),
            Punto(2, "Trabajo", "Calle Libertad 123", -12.0975, -77.0353)
        ), 3.2),
        Ruta(2, "Ruta San Isidro", listOf(
            Punto(2, "Trabajo", "Calle Libertad 123", -12.0975, -77.0353),
            Punto(3, "Gimnasio", "Av. Benavides 890", -12.1340, -77.0150)
        ), 2.8)
    )

    val bicisDisponibles = mutableListOf(
        Bici(1, "Trek FX3", "Estación Miraflores", true),
        Bici(2, "Giant Escape", "Estación Miraflores", true),
        Bici(3, "Cannondale Quick", "Estación San Isidro", true),
        Bici(4, "Specialized Sirrus", "Estación San Isidro", false),
        Bici(5, "Trek FX2", "Estación Barranco", true),
        Bici(6, "Giant Fastroad", "Estación Barranco", true)
    )

    val reservasActivas = mutableListOf<Reserva>()

    var contadorPuntos = 4
    var contadorRutas = 3
    var contadorReservas = 1
}