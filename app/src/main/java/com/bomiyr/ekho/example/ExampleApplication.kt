package com.bomiyr.ekho.example

import android.app.Application
import com.bomiyr.ekho.AndroidLogReflection
import com.bomiyr.ekho.Ekho

object EkhoLogger : Ekho()

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        EkhoLogger.addReflection(AndroidLogReflection(useDebugTags = true))

        EkhoLogger.i("Logger configured")
    }
}

