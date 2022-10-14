package com.example.foodtest

import android.app.Application
import com.example.foodtest.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        instance = this

    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(mainModule)
        }
    }

    companion object {
        var instance: App? = null
            private set
    }
}