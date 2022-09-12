package com.aengussong.mirror.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MirrorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
