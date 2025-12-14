package com.example.omadatest.di

import com.example.omadatest.app.OmadaApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(app: OmadaApplication)
}