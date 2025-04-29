package com.example.liho_mobile.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.liho_mobile.domain.models.Screens

@Composable
fun NavContainer(
    allScreens: List<Screens>,
    onTabSelected: (Screens) -> Unit,
    currentScreen: Screens,
) {
    Surface(
        modifier = Modifier.height(TabHeight).fillMaxWidth(),
    ) {
        Row(
            Modifier.selectableGroup(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            allScreens.forEach { screen ->
                screen.iconVector?.let {
                    NavTab(
                        text = screen.route,
                        icon = it,
                        onSelected = { onTabSelected(screen) },
                        selected = currentScreen == screen
                    )
                }
            }
        }
    }
}


@Composable
fun NavTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }

    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )

    Row(
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = rememberRipple(
//                    bounded = false,
//                    radius = Dp.Unspecified,
//                    color = Color.Unspecified
//                )
            )
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
    }
}

private const val TabFadeInAnimationDuration = 150
private const val TabFadeOutAnimationDuration = 100
private const val TabFadeInAnimationDelay = 100
private const val InactiveTabOpacity = 0.50f

private val TabHeight = 72.dp