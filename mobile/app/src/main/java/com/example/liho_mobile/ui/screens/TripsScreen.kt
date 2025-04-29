package com.example.liho_mobile.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liho_mobile.R
import com.example.liho_mobile.ui.components.GlassCard
import com.example.liho_mobile.ui.components.TripCard
import com.example.liho_mobile.ui.theme.spacing
import com.example.liho_mobile.ui.viewmodels.TripViewModel
import com.example.liho_mobile.ui.viewmodels.TripsState
import com.example.liho_mobile.ui.viewmodels.UserViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeSource
import org.koin.androidx.compose.koinViewModel


@Composable
fun TripsScreen(
    modifier: Modifier = Modifier,
    viewModel: TripViewModel = viewModel()
) {
    val tripsState by viewModel.tripsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.backgroundimage),
                contentScale = ContentScale.FillBounds
            )
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        when (tripsState) {
            is TripsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading.....")
                }
            }
            is TripsState.Success -> {
                val trips = (tripsState as TripsState.Success).trips
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(trips) { trip ->
                        TripCard(trip)
                        //GlassCard()
                    }
                }
            }

            is TripsState.Error -> {
                Text(text = "Error")
            }
            TripsState.Initial -> {
                Text(text = "Initial State")
            }
        }
    }
}

