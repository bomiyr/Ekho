package com.bomiyr.ekho.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        EkhoLogger.i { "onCreate called on ${MainActivity::class.java.name}" }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWorkingThread()

        findViewById<View>(R.id.button).setOnClickListener {
            EkhoLogger.d("Button clicked")
        }
    }

    private fun startWorkingThread() {
        thread {
            val startTime = System.currentTimeMillis()
            val logger = EkhoLogger.tagged("FixedTag")

            while (true) {
                val threadElapsed = System.currentTimeMillis() - startTime
                logger.i("${threadElapsed}ms since thread start")

                if (threadElapsed > 10_000) {
                    return@thread
                }
                sleep(2000L)
            }
        }
    }
}
