package com.bomiyr.ekho

import android.os.Build
import android.util.Log
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Config(sdk = [Build.VERSION_CODES.M, Build.VERSION_CODES.N], shadows = [ShadowLog::class])
@RunWith(RobolectricTestRunner::class)
internal class AndroidLogReflectionTest {
    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun correctLogLevels() {
        listOf(
            EkhoLevel.ERROR to Log.ERROR,
            EkhoLevel.ASSERT to Log.ASSERT,
            EkhoLevel.DEBUG to Log.DEBUG,
            EkhoLevel.INFO to Log.INFO,
            EkhoLevel.VERBOSE to Log.VERBOSE,
            EkhoLevel.WARN to Log.WARN
        ).forEach { (level, expected) ->
            checkLogLevel(level, expected)
        }
    }

    private fun checkLogLevel(level: EkhoLevel, expected: Int) {
        val reflection = AndroidLogReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(level, null, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(expected, value?.type, "Log levels are not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun generateDebugTag() {
        val logTarget = AndroidLogReflection(LogAll, useDebugTags = true)
        val message = UUID.randomUUID().toString()
        logTarget.log(EkhoLevel.ERROR, null, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals("AndroidLogReflectionTest", value?.tag, "Tags are not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun generateDebugTagEkho() {
        val ekho = Ekho()

        val logTarget = AndroidLogReflection(LogAll, useDebugTags = true)
        ekho.addReflection(logTarget)
        val message = UUID.randomUUID().toString()
        ekho.log(EkhoLevel.ERROR, null, null, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals("AndroidLogReflectionTest", value?.tag, "Tags are not the same")
    }

    class MyInnerClass {
        fun log(logTarget: AndroidLogReflection, message: String) {
            logTarget.log(EkhoLevel.ERROR, null, message, null)
        }
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun generateDebugTagOnInnerClass() {
        val message = UUID.randomUUID().toString()

        val logTarget = AndroidLogReflection(LogAll, useDebugTags = true)

        MyInnerClass().log(logTarget, message)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals("AndroidLogReflectionTest", value?.tag, "Tags are not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun doNotGenerateDebugTag() {
        val message = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, null, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(AndroidLogReflection.DEFAULT_TAG, value?.tag, "Tags are not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun correctTag() {
        val tag = "my_tag"
        val message = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(tag, value?.tag, "Tag is not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.M])
    @Test
    fun tagCutOnM() {
        val tag = "My_long_tag_that_is_more_than_twenty_three_characters"
        val tagCut = tag.substring(0, AndroidLogReflection.MAX_TAG_LENGTH)
        val message = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(tagCut, value?.tag, "Tag is not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun tagNotCutOnN() {
        val tag = "My_long_tag_that_is_more_than_twenty_three_characters"
        val message = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(tag, value?.tag, "Tag is not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun defaultTag() {
        val message = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, null, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.msg == message }

        assertEquals(AndroidLogReflection.DEFAULT_TAG, value?.tag, "Tag is not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun correctMessage() {
        val tag = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        val message = "My test message"
        logTarget.log(EkhoLevel.ERROR, tag, message, null)
        val logs = ShadowLog.getLogs()
        val value = logs.firstOrNull { it.tag == tag }

        assertEquals(message, value?.msg, "Messages are not the same")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun splitMessage() {
        val tag = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        val message = StringBuilder().apply {
            repeat(2500) { append("q") }
            repeat(2500) { append("w") }
            repeat(2500) { append("e") }
            repeat(2500) { append("r") }
        }.toString()
        logTarget.log(EkhoLevel.ERROR, tag, message, null)
        val logs = ShadowLog.getLogs()

        val loggedMessages = logs.filter { it.tag == tag }.map { it.msg }

        val allChunksLogged = message
            .chunked(AndroidLogReflection.MAX_LOG_LENGTH)
            .all {
                loggedMessages.contains(it)
            }

        assertTrue(allChunksLogged, "Not all chunks logged")
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun correctThrowable() {
        val tag = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, null, NullPointerException())
        val logs = ShadowLog.getLogs()

        val logItem = logs.firstOrNull { it.tag == tag }

        assertTrue(
            logItem?.msg?.contains(NullPointerException::class.java.name) == true,
            "No exception class in log message"
        )
    }

    @Config(sdk = [Build.VERSION_CODES.N])
    @Test
    fun correctMessageAndThrowable() {
        val tag = UUID.randomUUID().toString()
        val logTarget = AndroidLogReflection(LogAll)
        val message = "My test message"
        logTarget.log(EkhoLevel.ERROR, tag, message, NullPointerException())

        val logs = ShadowLog.getLogs()
        val logItem = logs.firstOrNull { it.tag == tag }

        val msg = logItem?.msg

        assertTrue(msg!!.startsWith(message), "Message is not logged")
        assertTrue(msg.contains(NullPointerException::class.java.name), "Throwable is not logged")
    }
}