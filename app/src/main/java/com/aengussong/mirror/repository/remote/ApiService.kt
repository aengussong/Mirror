package com.aengussong.mirror.repository.remote

interface ApiService {

    fun ping(url: String): Boolean
}