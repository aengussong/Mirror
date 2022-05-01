package com.aengussong.mirror.repository.remote

import javax.inject.Inject

class MirrorApiService @Inject constructor(): ApiService {
    override fun ping(url: String): Boolean {
        return false
    }
}