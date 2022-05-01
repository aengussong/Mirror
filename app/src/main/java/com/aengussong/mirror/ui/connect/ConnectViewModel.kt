package com.aengussong.mirror.ui.connect

import com.aengussong.mirror.model.CantConnect
import com.aengussong.mirror.model.ShowActions
import com.aengussong.mirror.repository.Repository
import com.aengussong.mirror.ui.base.BaseViewModel
import com.aengussong.mirror.util.Validator
import javax.inject.Inject

class ConnectViewModel @Inject constructor(private val repo: Repository) : BaseViewModel() {

    fun ping(ip: String, port: String) {
        Validator.validateAddress(ip, port)?.let {
            showMessage(it)
            return@ping
        }

        networkLaunch {
            val response = repo.ping(ip, port)
            if (response) {
                performAction(ShowActions)
            } else {
                showMessage(CantConnect)
            }
        }
    }

}