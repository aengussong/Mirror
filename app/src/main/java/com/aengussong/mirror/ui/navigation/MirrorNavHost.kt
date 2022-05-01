package com.aengussong.mirror.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.aengussong.mirror.ui.EnterAddress
import com.aengussong.mirror.ui.splash.Splash
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MirrorNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Navigation.Splash.asDestination()
    ) {
        composable(route = Navigation.Splash.asDestination(),
            exitTransition = {
                val tweenSpec = tween<IntOffset>(
                    durationMillis = 2000,
                    easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f)
                )
                slideOutVertically(targetOffsetY = { -it }, animationSpec = tweenSpec)
            },
            enterTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { null }) { Splash() }
        composable(Navigation.NoSavedAddress.asDestination()) { EnterAddress() }
    }
}