package com.example.helion

import android.app.Application
import com.example.helion.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val list = listOf(
            getLocalModule(),
            getRemoteModule(BuildConfig.BASE_URL),
            getDomainModule(),
            getUiModule()
        )

        startKoin {
            modules(list)
            androidContext(this@App)
        }
    }
}