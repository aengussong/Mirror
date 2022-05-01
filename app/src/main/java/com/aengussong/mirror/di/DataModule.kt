package com.aengussong.mirror.di

import com.aengussong.mirror.repository.Repository
import com.aengussong.mirror.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(repo: RepositoryImpl): Repository

}