package com.example.liho_mobile.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.liho_mobile.domain.models.Screens
import com.example.liho_mobile.ui.screens.TripDetailsContainer
import com.example.liho_mobile.ui.screens.CreateTripScreen
import com.example.liho_mobile.ui.screens.ProfileScreen
import com.example.liho_mobile.ui.screens.TripsScreen
import com.example.liho_mobile.ui.viewmodels.TripViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LihoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Trips.route,
    ) {
        composable(route = Screens.Trips.route) {
            val tripViewModel: TripViewModel = koinViewModel()
            TripsScreen(modifier, tripViewModel)

        }
        composable(route = Screens.CreateTrip.route) {
            CreateTripScreen()
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }

        composable(
            route = "${Screens.TripDetailsHome.route}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("id")

            tripId?.let {
                TripDetailsContainer(tripId = it)
            }
        }

//        navigation(
//            startDestination = Screens.TripDetailsHome.Summary.route,
//            route = "${Screens.TripDetailsHome.route}/{id}"
//        ) {
//            composable(
//                route = "${Screens.TripDetailsHome.Summary.route}/{id}",
//                arguments = listOf(
//                    navArgument("id") {
//                        type = NavType.StringType
//                    }
//                )
//            ) { backStackEntry ->
//                val tripViewModel: TripViewModel = koinViewModel()
//                val tripId = backStackEntry.arguments?.getString("id")
//
//                tripId?.let {
//                    TripDetailsScreen(tripViewModel, tripId = it)
//                }
//            }
//
//            composable(
//                route = "${Screens.TripDetailsHome.Accommodation.route}/{id}",
//                arguments = listOf(
//                    navArgument("id") {
//                        type = NavType.StringType
//                    }
//                )
//            ) { backStackEntry ->
//                val tripViewModel: TripViewModel = koinViewModel()
//                val tripId = backStackEntry.arguments?.getString("id")
//
//                tripId?.let {
//                    TripAccommodationScreen(tripViewModel, tripId = it)
//                }
//            }

//            composable(
//                route = "accommodation",
//                arguments = listOf(
//                    navArgument("id") {
//                        type = NavType.StringType
//                    }
//                )
//            ) { backStackEntry ->
//
//            }



        }
//        composable(route = CreateTrip.route) {
//            CreateTripScreen()
//        }
//        composable(route = Profile.route) {
//            ProfileScreen()
//        }
}
