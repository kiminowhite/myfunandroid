package com.knw.myfunandroid

import android.app.Application
import android.content.Context

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext
    }

    companion object {
        private var sContext: Context? = null

        fun getsContext(): Context? {
            return sContext
        }
    }
}
