package com.aengussong.mirror.control

import androidx.lifecycle.viewModelScope
import com.aengussong.mirror.ui.base.BaseViewModel
import com.aengussong.mirror.ui.navigation.Navigation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ControlViewModel : BaseViewModel() {

    private val genericState = listOf(
        Controller(Spook, false),
        Controller(MessageForObserver, false),
        Controller(ShowTemperature, false),
        Controller(OpenRemoteControl, false),
        Controller(Light, false),
        Controller(Guests, false),
    )

    val dataFlow: StateFlow<List<Controller>> = MutableStateFlow(emptyList())

    val errorFlow: SharedFlow<Navigation> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            dataFlow.emit(genericState)
        }
    }

    fun onControllerInteraction(controller: Controller) {
        viewModelScope.launch {
            val data = dataFlow.value.takeIf { it.isNotEmpty() } ?: genericState
            val index = data.indexOfFirst { it.type == controller.type }.takeIf { it >= 0 }
                ?: throw IllegalStateException()
            val updatedData = data.toMutableList().also {
                it[index] = Controller(controller.type, !controller.isOn)
            }
            dataFlow.emit(updatedData)
        }
    }
}

class Controller(val type: ControllerType, val isOn: Boolean)

sealed interface ControllerType
object Spook : ControllerType
object MessageForObserver : ControllerType
object ShowTemperature : ControllerType
object OpenRemoteControl : ControllerType
object Light : ControllerType
object Guests : ControllerType