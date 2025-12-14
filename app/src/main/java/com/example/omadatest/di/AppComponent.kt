package com.example.omadatest.di

import com.example.omadatest.MainActivity
import com.example.omadatest.data.RepositoryModule
import com.example.omadatest.network.NetworkModule
import com.example.omadatest.ui.screens.PhotosViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun viewModelFactory(): PhotosViewModelFactory
}