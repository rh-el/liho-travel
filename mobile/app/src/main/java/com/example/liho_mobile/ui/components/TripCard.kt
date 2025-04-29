package com.example.liho_mobile.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.LocalHazeState
import com.example.liho_mobile.LocalNavController
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.domain.models.Screens
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials



@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun TripCard(trip: Trip) {
    val navController = LocalNavController.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${Screens.TripDetailsHome.route}/${trip.id}") }
            .hazeEffect(state = LocalHazeState.current)
            .height(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(com.example.liho_mobile.R.drawable.palmtree),
                contentDescription =  "an icon representing the trip",
                modifier = Modifier
                    .fillMaxHeight(),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = trip.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "${trip.startDate} - ${trip.endDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


@Preview
@Composable
fun TripCardPreview() {
    val trip = Trip(
        name = "Voyage à Tokyo",
        id = 0,
        createdBy = 1,
        nbParticipants = 4,
        destination = "Tokyo",
        startDate = "2025-03-15",
        endDate = "2025-03-25",
        description = "yum ramen yum",
        createdAt = "15612561",
        updatedAt = "4156465",
    )
    TripCard(trip)
}