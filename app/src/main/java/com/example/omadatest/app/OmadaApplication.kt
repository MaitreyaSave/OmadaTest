package com.example.omadatest.app

import android.app.Application
import com.example.omadatest.di.AppComponent
import com.example.omadatest.di.DaggerAppComponent

class OmadaApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}
