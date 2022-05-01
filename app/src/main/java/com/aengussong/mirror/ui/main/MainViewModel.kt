package com.aengussong.mirror.ui.main

import androidx.lifecycle.viewModelScope
import com.aengussong.mirror.repository.Repository
import com.aengussong.mirror.ui.base.BaseViewModel
import com.aengussong.mirror.ui.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : BaseViewModel() {

    val isSplashReady: Boolean
        get() = isDataSetInitialized.get()

    private var isDataSetInitialized = AtomicBoolean(false)

    val navigation: SharedFlow<Navigation> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            initializeState()
            isDataSetInitialized.set(true)
        }
    }

    private suspend fun initializeState() {
        if (!repo.hasSavedAddress()) {
            navigation.emit(Navigation.NoSavedAddress)
        }
    }
}