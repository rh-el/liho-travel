package com.example.liho_mobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.R
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.data.models.TripWithDetails
import com.example.liho_mobile.ui.components.AccommodationCard
import com.example.liho_mobile.ui.components.DescriptionCard
import com.example.liho_mobile.ui.components.DetailsCard
import com.example.liho_mobile.ui.components.PriceCard
import com.example.liho_mobile.ui.theme.spacing
import com.example.liho_mobile.ui.viewmodels.TripDetailState
import com.example.liho_mobile.ui.viewmodels.TripViewModel

@Composable
fun TripDetailsSummary(trip: TripWithDetails) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DetailsCard("${trip.startDate} ${trip.endDate}", trip.destination, trip.participants)
            DescriptionCard(trip.description)
            AccommodationCard(trip.destination)
            PriceCard()
        }
    }
}
