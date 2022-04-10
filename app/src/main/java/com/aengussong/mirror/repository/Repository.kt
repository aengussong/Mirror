package com.aengussong.mirror.repository

import com.aengussong.mirror.proto.MirrorAddress
import com.aengussong.mirror.repository.remote.response.PingResponse

interface Repository {

    suspend fun ping(host:String, port:String):PingResponse

    suspend fun pingSaved():PingResponse

    suspend fun getSavedAddress():MirrorAddress?

    suspend fun saveAddress(ip:String, port:Int)

    suspend fun hasSavedAddress():Boolean
}

