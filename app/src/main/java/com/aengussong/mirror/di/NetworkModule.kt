package com.aengussong.mirror.di

import com.aengussong.mirror.repository.remote.ApiService
import com.aengussong.mirror.repository.remote.MirrorApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindApiService(repo: MirrorApiService): ApiService

}