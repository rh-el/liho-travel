package com.example.liho_mobile.ui.screens

import Accommodation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.ui.components.TripAccommodationCard
import com.example.liho_mobile.ui.viewmodels.AccommodationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TripAccommodationScreen(tripAccommodation: List<Accommodation>) {

    val accommodationViewModel: AccommodationViewModel = koinViewModel()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tripAccommodation) { accommodation ->
            TripAccommodationCard(accommodation, { accommodationViewModel.toggleLike(it)})
        }
    }
}