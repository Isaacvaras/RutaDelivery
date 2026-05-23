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

object AppState {
    var usuarioActual = Usuario(
        nombre = "Isaac Varas",
        email = "isaac.varas@citybike.pe",
        telefono = "+51 987 654 321",
        password = "citybike123"
    )

    val puntosGuardados = mutableListOf<Punto>()

    val rutasGuardadas = mutableListOf<Ruta>()

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