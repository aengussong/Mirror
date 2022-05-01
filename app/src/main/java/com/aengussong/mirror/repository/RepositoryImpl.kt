package com.aengussong.mirror.repository

import com.aengussong.mirror.repository.remote.ApiService
import com.aengussong.mirror.proto.MirrorAddress
import kotlinx.coroutines.delay
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun ping(host: String, port: String): Boolean {
        val url = "$host:$port/api/test"
        return apiService.ping(url)
    }

    override suspend fun getSavedAddress(): MirrorAddress? {
        return null
    }

    override suspend fun saveAddress(ip: String, port: Int) {

    }

    override suspend fun hasSavedAddress(): Boolean {
        delay(3000)
        return false
    }
}
