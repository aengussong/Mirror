package com.aengussong.mirror.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Red = Color(0xFFc81515)

private val DarkColors = darkColors(
    primary = Color(0xFF121212),
    secondary = Red
)

private val LightColors = lightColors(
    primary = Color(0xFF000000),
    secondary = Red
)

@Composable
fun MirrorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}