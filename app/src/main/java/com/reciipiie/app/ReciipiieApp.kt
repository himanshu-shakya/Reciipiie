package com.reciipiie.app

import android.app.Application
import com.reciipiie.app.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class ReciipiieApp():Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ReciipiieApp)
            modules(appModules)
        }
    }
}