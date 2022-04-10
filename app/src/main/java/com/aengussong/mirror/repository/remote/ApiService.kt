package com.aengussong.mirror.repository.remote

import com.aengussong.mirror.repository.remote.response.PingResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    fun ping(@Url url: String): PingResponse
}