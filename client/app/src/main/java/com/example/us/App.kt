package com.example.us

import android.app.Application
import com.example.us.api.CacheClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CacheClient.init(this)
    }
}
