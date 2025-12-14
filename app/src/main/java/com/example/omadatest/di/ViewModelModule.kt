package com.example.omadatest.di

import com.example.omadatest.data.FlickrRepository
import com.example.omadatest.ui.features.photos.PhotosViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: FlickrRepository): PhotosViewModelFactory {
        return PhotosViewModelFactory(repository)
    }
}