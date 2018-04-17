package com.jsonapiexample

import android.app.Application
import com.jsonapiexample.di.appModule
import org.koin.android.ext.android.startKoin


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, appModule)
    }
}