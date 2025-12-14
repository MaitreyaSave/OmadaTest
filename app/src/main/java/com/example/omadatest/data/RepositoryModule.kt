package com.example.omadatest.data

import com.example.omadatest.network.FlickrApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFlickrRepository(apiService: FlickrApiService): FlickrRepository {
        return FlickrRepository(apiService)
    }
}
