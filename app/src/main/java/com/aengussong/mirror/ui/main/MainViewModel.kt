package com.aengussong.mirror.ui.main

import androidx.lifecycle.viewModelScope
import com.aengussong.mirror.model.navigation.NoSavedAddress
import com.aengussong.mirror.model.navigation.Navigation
import com.aengussong.mirror.repository.Repository
import com.aengussong.mirror.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : BaseViewModel() {

    val navigation: SharedFlow<Navigation> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            if (!repo.hasSavedAddress()) {
                navigation.emit(NoSavedAddress)
            }
        }
    }
}