package com.aengussong.mirror.connecting

import androidx.lifecycle.viewModelScope
import com.aengussong.mirror.ui.base.BaseViewModel
import com.aengussong.mirror.ui.navigation.Navigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

private const val DEFAULT_PORT = "8080"

class ConnectingViewModel : BaseViewModel() {

    val connectingAddressFlow: SharedFlow<String> = MutableSharedFlow()

    val navigationFlow: SharedFlow<Navigation> = MutableSharedFlow()

    fun onAddressSet(ip: String?, port: String?) {
        viewModelScope.launch {
            val validPort = port ?: DEFAULT_PORT
            if (ip == null) {
                startScanning()
            } else {
                connect("$ip:$validPort")
            }
        }
    }

    private fun startScanning() {
        viewModelScope.launch {
            val baseAddress = "192.168.0.2"
            repeat(10) {
                delay(1000)
                connectingAddressFlow.emit("$baseAddress$it:$DEFAULT_PORT")
            }
            navigationFlow.emit(Navigation.ControlMirror)
        }
    }

    private fun connect(address: String) {
        viewModelScope.launch {
            connectingAddressFlow.emit(address)
            delay(5000)
            navigationFlow.emit(Navigation.ControlMirror)
        }
    }

}