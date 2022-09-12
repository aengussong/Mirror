package com.aengussong.mirror.ui.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import com.aengussong.mirror.ui.navigation.MirrorNavHost
import com.aengussong.mirror.ui.navigation.Navigation
import com.aengussong.mirror.ui.theme.MirrorTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MirrorTheme {
                val navController = rememberAnimatedNavController()
                MirrorNavHost(navController = navController)

                val navigation: Navigation? by viewModel.navigation.collectAsState(initial = null)
                LaunchedEffect(navigation) {
                    navigation?.let {
                        // pop Splash from backstack
                        navController.popBackStack(0, inclusive = true)
                        navController.navigate(it.asDestination())
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            handleSplash()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun handleSplash() {
        retainSplashUntilDataInitialized()
        addSplashExitAnimation()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun addSplashExitAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            with(slideUp) {
                interpolator = AnticipateInterpolator()
                duration = 750L

                doOnEnd { splashScreenView.remove() }

                start()
            }
        }
    }

    private fun retainSplashUntilDataInitialized() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (viewModel.isSplashReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                }
                return viewModel.isSplashReady
            }
        })
    }
}
