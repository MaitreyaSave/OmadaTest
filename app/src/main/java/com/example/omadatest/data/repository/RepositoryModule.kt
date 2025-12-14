package com.example.omadatest.data.repository

import com.example.omadatest.data.network.FlickrApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFlickrRepository(apiService: FlickrApiService): FlickrRepository {
        return FlickrRepositoryImpl(apiService)
    }
}