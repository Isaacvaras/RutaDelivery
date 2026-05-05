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
import com.tienda.rutadelivery.screens.OrdenScreen
import com.tienda.rutadelivery.screens.PerfilScreen
import com.tienda.rutadelivery.screens.RutaScreen

sealed class Screen {
    object Login    : Screen()
    object Register : Screen()
    object Map      : Screen()
    object Orden    : Screen()
    object Ruta     : Screen()
    object Perfil   : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                RutaDeliveryApp()
            }
        }
    }
}

@Composable
fun RutaDeliveryApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    when (currentScreen) {
        is Screen.Login -> LoginScreen(
            onLogin = { currentScreen = Screen.Map },
            onRegister = { currentScreen = Screen.Register }
        )
        is Screen.Register -> RegisterScreen(
            onBack = { currentScreen = Screen.Login }
        )
        is Screen.Map -> MapScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToRuta = { currentScreen = Screen.Ruta },
            onNavigateToPerfil = { currentScreen = Screen.Perfil },
            onVerOrden = { currentScreen = Screen.Orden }
        )
        is Screen.Orden -> OrdenScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToRuta = { currentScreen = Screen.Ruta },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Ruta -> RutaScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToRuta = { currentScreen = Screen.Ruta },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
        is Screen.Perfil -> PerfilScreen(
            onNavigateToMap = { currentScreen = Screen.Map },
            onNavigateToRuta = { currentScreen = Screen.Ruta },
            onNavigateToPerfil = { currentScreen = Screen.Perfil }
        )
    }
}