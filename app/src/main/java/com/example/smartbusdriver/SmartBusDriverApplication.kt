package com.example.smartbusdriver

import android.app.Application
import com.example.smartbusdriver.di.dataModule
//import com.example.smartbusdriver.di.dataModule
import com.example.smartbusdriver.di.networkModule
import com.example.smartbusdriver.di.presentationModule
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
//import com.example.smartbusdriver.di.presentationModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SmartBusDriverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("6af80549-c660-4137-8516-d039ad43dc6e")
        MapKitFactory.initialize(this)
        setupDi()
    }

    private fun setupDi() {
        startKoin {
            androidLogger()
            //androidContext(this@SmartBusPassengerApplication)
            modules(networkModule, dataModule, presentationModule)
        }
    }
}