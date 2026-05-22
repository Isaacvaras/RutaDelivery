package com.tienda.rutadelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tienda.rutadelivery.screens.LoginScreen
import com.tienda.rutadelivery.screens.RegisterScreen
import com.tienda.rutadelivery.screens.HomeScreen
import com.tienda.rutadelivery.screens.MapScreen
import com.tienda.rutadelivery.screens.PerfilScreen
import com.tienda.rutadelivery.screens.RutaScreen
import com.tienda.rutadelivery.screens.AlquilerScreen

sealed class Screen {
    object Login    : Screen()
    object Home     : Screen()
    object Mapa     : Screen()
    object Rutas    : Screen()
    object Alquiler : Screen()
    object Perfil   : Screen()
    object Register : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                CityBikeApp()
            }
        }
    }
}

@Composable
fun CityBikeApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        is Screen.Login -> LoginScreen(
            onLogin = { currentScreen = Screen.Home },
            onRegister = { currentScreen = Screen.Register }
        )
        is Screen.Register -> RegisterScreen(
            onBack = { currentScreen = Screen.Login },
            onRegisterSuccess = { currentScreen = Screen.Home }
        )
        is Screen.Home -> HomeScreen(
            onNavigateToMapa = { currentScreen = Screen.Mapa },
            onNavigateToRutas = { currentScreen = Screen.Rutas },
            onNavigateToAlquiler = { currentScreen = Screen.Alquiler },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Mapa -> MapScreen(
            onNavigateToHome = { currentScreen = Screen.Home },
            onNavigateToRutas = { currentScreen = Screen.Rutas },
            onNavigateToAlquiler = { currentScreen = Screen.Alquiler },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Rutas -> RutaScreen(
            onNavigateToHome = { currentScreen = Screen.Home },
            onNavigateToMapa = { currentScreen = Screen.Mapa },
            onNavigateToAlquiler = { currentScreen = Screen.Alquiler },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Alquiler -> AlquilerScreen(
            onNavigateToHome = { currentScreen = Screen.Home },
            onNavigateToMapa = { currentScreen = Screen.Mapa },
            onNavigateToRutas = { currentScreen = Screen.Rutas },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Perfil -> PerfilScreen(
            onNavigateToHome = { currentScreen = Screen.Home },
            onNavigateToMapa = { currentScreen = Screen.Mapa },
            onNavigateToRutas = { currentScreen = Screen.Rutas },
            onNavigateToAlquiler = { currentScreen = Screen.Alquiler },
            onLogout = { currentScreen = Screen.Login },
            onRegister = { currentScreen = Screen.Register }
        )
    }
}