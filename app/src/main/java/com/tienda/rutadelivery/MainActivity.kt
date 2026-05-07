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
import com.tienda.rutadelivery.screens.MapScreen
import com.tienda.rutadelivery.screens.UbicacionesScreen
import com.tienda.rutadelivery.screens.PerfilScreen
import com.tienda.rutadelivery.screens.RutaScreen

sealed class Screen {
    object Login       : Screen()
    object Register    : Screen()
    object Map         : Screen()
    object Ubicaciones : Screen()
    object Perfil      : Screen()
    object Ruta        : Screen()
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
            onLogin = { currentScreen = Screen.Map },
            onRegister = { currentScreen = Screen.Register }
        )
        is Screen.Register -> RegisterScreen(
            onBack = { currentScreen = Screen.Login },
            onRegisterSuccess = { currentScreen = Screen.Login }
        )
        is Screen.Map -> MapScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToUbicaciones = { currentScreen = Screen.Ubicaciones }
        )
        is Screen.Ubicaciones -> UbicacionesScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToUbicaciones = { currentScreen = Screen.Ubicaciones }
        )
        is Screen.Perfil -> PerfilScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToUbicaciones = { currentScreen = Screen.Ubicaciones },
            onNavigateToPerfil = { currentScreen = Screen.Perfil },
            onLogout = { currentScreen = Screen.Login }
        )
        is Screen.Ruta -> RutaScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToUbicaciones = { currentScreen = Screen.Ubicaciones },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
    }
}