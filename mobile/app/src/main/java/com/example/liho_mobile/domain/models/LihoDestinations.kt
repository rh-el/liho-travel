package com.example.liho_mobile.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.liho_mobile.R


sealed class Screens(val route: String, val iconVector: ImageVector? = null, val iconDrawable: Int? = null) {
    object Trips: Screens("home", Icons.Filled.Place)
    object CreateTrip: Screens("create", Icons.Filled.Add)
    object Profile: Screens("profile", Icons.Filled.Person)
    object TripDetailsHome: Screens("trip-details")
}


val NavRowScreens = listOf(Screens.Trips, Screens.CreateTrip, Screens.Profile)