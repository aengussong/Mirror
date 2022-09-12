package com.aengussong.mirror.enter_address

import androidx.lifecycle.viewModelScope
import com.aengussong.mirror.ui.base.BaseViewModel
import com.aengussong.mirror.ui.navigation.Navigation
import com.aengussong.mirror.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class EnterAddressViewModel @Inject constructor() : BaseViewModel() {

    val errorFlow: SharedFlow<Error> = MutableSharedFlow()
    val navigationFlow: SharedFlow<Navigation> = MutableSharedFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is Error) {
            viewModelScope.launch {
                errorFlow.emit(throwable)
            }
        } else {
            throw throwable
        }
    }
    private val errorHandlerScope =
        CoroutineScope(Dispatchers.Default + errorHandler + SupervisorJob())

    fun onScan(ip: String, port: String) {
        errorHandlerScope.launch {
            validate(ip, port)

            navigationFlow.emit(Navigation.Connect(ip, port))
        }
    }

    fun onAutomaticScan() {
        errorHandlerScope.launch {
            navigationFlow.emit(Navigation.AutomaticScan)
        }
    }

    private fun validate(ip: String, port: String) {
        if (!Validator.validateIp(ip)) throw IpValidationError
        if (!Validator.validatePort(port)) throw PortValidationError
    }

    override fun onCleared() {
        errorHandlerScope.cancel()
    }
}

sealed interface Error {
    val message: String?
}

object IpValidationError : Error, Exception("Ip isn't valid")
object PortValidationError : Error, Exception("Port isn't valid")