package com.aengussong.mirror.di

import com.aengussong.mirror.repository.Repository
import com.aengussong.mirror.repository.RepositoryImpl
import com.aengussong.mirror.repository.remote.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module(includes = [DataModule.DataBuilderModule::class])
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(repo: RepositoryImpl): Repository

    @Module
    @InstallIn(ViewModelComponent::class)
    internal object DataBuilderModule {
        @Provides
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder().build()
        }

        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit
                .create(ApiService::class.java)

        }
    }
}