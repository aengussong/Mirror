package com.aengussong

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aengussong.mirror.ui.navigation.Navigation

@Composable
fun ConnectingScreen(ip: String? = null, port: String? = null, onNavigate: (Navigation) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color.Yellow))
}