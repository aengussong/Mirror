package com.aengussong.mirror.ui.navigation

sealed class Navigation(private val destination: String) {
    fun asDestination(): String = destination

    object Splash: Navigation("splash")

    object NoSavedAddress: Navigation("enter_address")
}
