package com.gtsl.breakingbad.presentation

import android.app.Application
import com.gtsl.breakingbad.di.netModule
import com.gtsl.breakingbad.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    netModule,
                    presentationModule
                )
            )
        }
    }
}
