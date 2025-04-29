package com.example.liho_mobile.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.LocalHazeState
import com.example.liho_mobile.LocalNavController
import com.example.liho_mobile.R
import com.example.liho_mobile.data.models.Participant
import com.example.liho_mobile.ui.theme.spacing
import dev.chrisbanes.haze.hazeEffect

@Composable
fun DetailsCard(date: String, destination: String, participants: List<Participant>) {

    val formattedDate = replaceStr(replaceStr(date, "-", "/"), " ", " - ")
    val formattedParticipantsStr = formatParticipantsStr(participants)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(R.drawable.calendar),
                        contentDescription =  "a calendar icon",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(R.drawable.place),
                        contentDescription =  "a localization icon",
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                Text(
                    text = destination,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(R.drawable.participants),
                        contentDescription = "a users icon",
                        modifier = Modifier.size(26.dp)
                    )
                }
                Text(
                    text = formattedParticipantsStr,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
    }
}



fun replaceStr(input: String, oldChar: String, newChar: String): String = input.replace(oldChar, newChar)

fun formatParticipantsStr(input: List<Participant>): String {
    var str = ""
    input.forEach {
        str += "${it.username.replaceFirstChar { char -> char.titlecase() }}, "
    }
    return str
}