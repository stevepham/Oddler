package com.ht117.oddler

import android.app.Application
import com.ht117.data.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OddlerApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OddlerApp)
            modules(listOf(dataModule, appModule))
        }
    }
}
