package com.example.liho_mobile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.LocalHazeState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GlassCard() {
    Surface(
        color = Color.White.copy(alpha = 0.2f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .hazeEffect(state = LocalHazeState.current, style = HazeMaterials.ultraThin())
            .height(80.dp)
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(text = "blabalblablablalb")
    }
}