package com.example.liho_mobile.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.liho_mobile.LocalNavController

@Composable
fun ComposeLocalWrapper(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalNavController provides rememberNavController(),
        content = content
    )
}