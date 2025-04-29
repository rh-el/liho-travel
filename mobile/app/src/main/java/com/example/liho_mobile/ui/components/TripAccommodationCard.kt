package com.example.liho_mobile.ui.components

import Accommodation
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liho_mobile.R
import com.example.liho_mobile.ui.viewmodels.AccommodationViewModel
import com.example.liho_mobile.ui.viewmodels.TripViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TripAccommodationCard(
    tripAccommodation: Accommodation,
    onLikeClick: (Accommodation) -> Unit
    ) {

    val dailyPrice = tripAccommodation.price / 7

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Column(
             verticalArrangement = Arrangement.spacedBy(16.dp),
             modifier = Modifier
                 .fillMaxWidth()

            ) {
                Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                    painterResource(R.drawable.accommodation),
                    contentDescription = "an icon of a house",
                    modifier = Modifier.size(24.dp)
                )
                    Text(
                        text = tripAccommodation.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                     painterResource(R.drawable.place),
                     contentDescription = "an icon of a pin",
                     modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = tripAccommodation.address,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                     painterResource(R.drawable.euros),
                     contentDescription = "an icon of euros",
                     modifier = Modifier.size(24.dp)
                    )
                    Column(
                     verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Total price",
                            style = MaterialTheme.typography.titleMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                        Text(
                            text = "${tripAccommodation.price.toString()} €",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Price per day",
                            style = MaterialTheme.typography.titleMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                        Text(
                            text = "${dailyPrice.toString()} €",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

                Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                     painterResource(R.drawable.bed),
                     contentDescription = "an icon of a bed",
                     modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "${tripAccommodation.capacity.toString()} beds",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Row(
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                     painterResource(R.drawable.desc),
                     contentDescription = "an icon of description",
                     modifier = Modifier
                         .size(24.dp)
                         .clickable {  }

                    )
                    tripAccommodation.additionalInformation?.let {
                        Text(
                            text = it,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painterResource(R.drawable.like),
                    contentDescription = "an icon of a heart",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onLikeClick(tripAccommodation) }
                )
                Text(
                    text = "${tripAccommodation.isLikedBy.size}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

    }
}

@Preview
@Composable
fun TripAccommodationCardPreview() {
    TripAccommodationCard(
        Accommodation.default(),
        onLikeClick = TODO()
    )
}