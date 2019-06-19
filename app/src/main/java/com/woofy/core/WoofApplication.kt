package com.woofy.core

import android.app.Application
import com.woofy.net.ApiManager
import com.woofy.net.RestAPI

class WoofApplication : Application() {
    companion object {
        lateinit var instance: WoofApplication
    }

    internal lateinit var netManager: RestAPI

    override fun onCreate() {
        super.onCreate()
        instance = this

        netManager = ApiManager()
        ResourcesRepository.with(applicationContext)
    }
}

