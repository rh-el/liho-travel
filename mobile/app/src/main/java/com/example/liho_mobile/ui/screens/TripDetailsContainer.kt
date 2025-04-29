package com.example.liho_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liho_mobile.R
import com.example.liho_mobile.ui.theme.spacing
import com.example.liho_mobile.ui.viewmodels.TripDetailState
import com.example.liho_mobile.ui.viewmodels.TripViewModel
import org.koin.androidx.compose.koinViewModel


data class TabRowItem(
    val title: String,
    val icon: Int,
    val screen: @Composable () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsContainer(tripId: String) {

    val tripViewModel: TripViewModel = koinViewModel()
    val tripDetailState by tripViewModel.tripDetailState.collectAsState()

    LaunchedEffect(tripId) {
        tripViewModel.fetchTripDetail(tripId)
    }

    var selectedTabIndex by remember { mutableStateOf(0) }

    when (tripDetailState) {
        TripDetailState.Loading -> CircularProgressIndicator()
        is TripDetailState.Success -> {
            val trip = (tripDetailState as TripDetailState.Success).trip

            val tabs = listOf(
                TabRowItem(
                    title = "Details",
                    screen = { TripDetailsSummary(trip) },
                    icon = R.drawable.details,
                ),
                TabRowItem(
                    title = "Accommodation",
                    screen = { TripAccommodationScreen(trip.accommodation) },
                    icon = R.drawable.accommodation,
                ),
                TabRowItem(
                    title = "Transport",
                    screen = { TripTransportScreen(trip) },
                    icon = R.drawable.transport,
                ),
                TabRowItem(
                    title = "Activities",
                    screen = { TripActivitiesScreen(trip) },
                    icon = R.drawable.activities,
                ),
                TabRowItem(
                    title = "Price",
                    screen = { TripPriceScreen(trip) },
                    icon = R.drawable.euros,
                ),
            )

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
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = trip.destination,
                        fontSize = 60.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        indicator = {},
                        divider = {},
                        modifier = Modifier
                            .background(color = Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(24.dp))
                        ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
//                                text = { Text(tab.title) },
                                icon = { Icon(
                                    painter = painterResource(id = tab.icon),
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(
                                        if (selectedTabIndex == index) {
                                            28.dp
                                        } else {
                                            20.dp
                                        })
                                )},
                                selectedContentColor = Color.Black,
                                unselectedContentColor = Color.Black,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = if (selectedTabIndex == index) {
                                            Color.White.copy(alpha = .5f)
                                        } else {
                                            Color.Transparent
                                        },
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            )
                        }
                    }

                    tabs[selectedTabIndex].screen()
                }
            }
        }

        is TripDetailState.Error -> Text(
            text = (tripDetailState as TripDetailState.Error).message,
            modifier = Modifier.padding(40.dp)
        )
        TripDetailState.Initial -> Text(text = "initial")
    }
}

