package com.aengussong.mirror.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.*
import com.aengussong.mirror.connecting.ConnectingScreen
import com.aengussong.mirror.control.ControlScreen
import com.aengussong.mirror.enter_address.EnterAddressScreen
import com.aengussong.mirror.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MirrorNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Navigation.Splash.asDestination()
    ) {
        mirrorComposable(route = Navigation.Splash.asDestination()) { SplashScreen() }
        mirrorComposable(route = Navigation.NoSavedAddress.asDestination()) {
            navController.popBackStack(0, inclusive = true)
            EnterAddressScreen {
                navController.navigate(
                    it.asDestination()
                )
            }
        }
        val arguments = arrayOf(
            navArgument("ip") { type = NavType.StringType },
            navArgument(name = "port") { type = NavType.StringType }
        )
        mirrorComposable(route = asDestination<Navigation.Connect>(*arguments)) { entry ->
            ConnectingScreen(
                ip = entry.arguments?.getString("ip"),
                port = entry.arguments?.getString("port")
            ) {
                if (entry.arguments == null) {
                    navController.popBackStack(0, inclusive = true)
                }
                navController.navigate(
                    it.asDestination()
                )
            }
        }
        mirrorComposable(route = Navigation.AutomaticScan.asDestination()) {
            ConnectingScreen {
                navController.navigate(
                    it.asDestination()
                )
            }
        }
        mirrorComposable(route = Navigation.ControlMirror.asDestination()) {
            navController.popBackStack(0, inclusive = true)
            ControlScreen {
                navController.navigate(
                    it.asDestination()
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.slideOutUp(): ExitTransition {
    val tweenSpec = tween<IntOffset>(
        durationMillis = 2000,
        easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f)
    )
    return slideOutVertically(targetOffsetY = { -it }, animationSpec = tweenSpec)
}

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentScope<*>.slideInDown(): EnterTransition {
    val tweenSpec = tween<IntOffset>(
        durationMillis = 2000,
        easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f)
    )
    return slideInVertically(initialOffsetY = { it }, animationSpec = tweenSpec)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mirrorComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        exitTransition = { slideOutUp() },
        popEnterTransition = { slideInDown() },
        popExitTransition = { slideOutUp() },
        content = content
    )
}