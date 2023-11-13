package com.example.cryptoappwithkoin

import android.app.Application
import com.example.cryptoappwithkoin.di.anotherModule
import com.example.cryptoappwithkoin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(appModule, anotherModule)
        }
    }
}