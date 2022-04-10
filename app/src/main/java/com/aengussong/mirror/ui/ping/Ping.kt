package com.aengussong.mirror.ui.ping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Ping() {
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color(10, 10, 10)))
}

@Preview
@Composable
private fun Preview() {
    Ping()
}