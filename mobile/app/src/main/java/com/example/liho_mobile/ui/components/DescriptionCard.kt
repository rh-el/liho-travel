package com.example.liho_mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.R
import com.example.liho_mobile.ui.theme.spacing

@Composable
fun DescriptionCard(content: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.desc),
                    contentDescription = "an information icon",
                    modifier = Modifier
                        .size(28.dp)
                )
            }
            Text(
                text = content,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}

//@Preview
//@Composable
//fun DescriptionCardPreview() {
//    val content = "a Support store him amount add investment including husband. Notice space all never. Behavior face significant guy citizen. "
//    DescriptionCard(content)
//}