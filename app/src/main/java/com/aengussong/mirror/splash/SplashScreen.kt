package com.aengussong.mirror.splash

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aengussong.mirror.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ) {
        var atEnd by remember { mutableStateOf(false) }
        val animatedPainter = rememberAnimatedVectorPainter(
            AnimatedImageVector.animatedVectorResource(id = R.drawable.load_animation),
            atEnd
        )
        val painter =
            rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.load_static))

        suspend fun runAnimation() {
            while (true) {
                atEnd = !atEnd
                delay(3600) //animation delay
            }
        }

        Icon(
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center),
            // in order to infinitely play animation, without reverse animation played, change
            // to the static start vector after animation end (to check out reverse animation
            // leave only animatedPainter). The animation won't be infinite, the still image and
            // animation will alternate.
            painter = if (!atEnd) painter else animatedPainter,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "loader"
        )

        LaunchedEffect(animatedPainter) {
            runAnimation()
        }
    }
}
