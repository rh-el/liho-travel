package com.example.liho_mobile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.domain.models.NavRowScreens
import com.example.liho_mobile.domain.models.Screens
import com.example.liho_mobile.ui.components.LihoNavHost
import com.example.liho_mobile.ui.components.NavContainer
import com.example.liho_mobile.ui.components.TripCard
import com.example.liho_mobile.ui.theme.LihomobileTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}

val LocalHazeState = compositionLocalOf { HazeState() }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LihoApp() {
    LihomobileTheme() {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = NavRowScreens.find { it.route == currentDestination?.route } ?: Screens.Trips
        val hazeState = remember { HazeState() }


        CompositionLocalProvider(
            LocalNavController provides navController,
            LocalHazeState provides hazeState
            ) {
            Scaffold(
                bottomBar = {
                    NavContainer(
                        allScreens = NavRowScreens,
                        onTabSelected = { newScreen ->
                            navController.navigate(newScreen.route)
                        },
                        currentScreen = currentScreen,
                    )
                }
            ) {
                LihoNavHost(
                    navController = navController,
                )
            }
        }
    }
}