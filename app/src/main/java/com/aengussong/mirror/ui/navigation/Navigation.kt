package com.aengussong.mirror.ui.navigation

import androidx.navigation.NamedNavArgument

sealed interface Navigation {
    fun asDestination(): String = this::class.java.simpleName

    object Splash : Navigation

    object NoSavedAddress : Navigation

    class Connect(val ip: String, val port: String) : Navigation {
        override fun asDestination(): String {
            return super.asDestination() + "/$ip/$port"
        }
    }
    object AutomaticScan : Navigation

    object ControlMirror: Navigation
}

// todo passing arguments in the wrong order (right order declared in the route, returned by the
//  [Navigation.asDestination] function) will result in possible bugs and crashes. Should somehow
//  restrict API user from passing arguments in wrong order. Also argument names are duplicated
//  in several places in the NavHost.
inline fun <reified T : Navigation> asDestination(vararg params: NamedNavArgument): String =
    T::class.java.simpleName + params.joinToString(separator = "") { "/{${it.name}}" }
