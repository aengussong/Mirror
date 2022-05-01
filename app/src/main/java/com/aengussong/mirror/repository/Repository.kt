package com.aengussong.mirror.repository

import com.aengussong.mirror.proto.MirrorAddress

interface Repository {

    suspend fun ping(host: String, port: String): Boolean

    suspend fun getSavedAddress(): MirrorAddress?

    suspend fun saveAddress(ip: String, port: Int)

    suspend fun hasSavedAddress(): Boolean
}

