package com.bomiyr.ekho

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.test.*

class SystemOutReflectionTest {

    private val standardOut: PrintStream = System.out
    private val outputStreamCaptor: ByteArrayOutputStream = ByteArrayOutputStream()

    @BeforeTest
    fun redirectSystemOut() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterTest
    fun returnStandardOut() {
        System.setOut(standardOut)
    }

    @Test
    fun correctLogLevelError() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.ERROR, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("E/") }
    }

    @Test
    fun correctLogLevelAssert() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.ASSERT, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("A/") }
    }

    @Test
    fun correctLogLevelDebug() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.DEBUG, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("D/") }
    }

    @Test
    fun correctLogLevelInfo() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.INFO, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("I/") }
    }

    @Test
    fun correctLogLevelVerbose() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.VERBOSE, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("V/") }
    }

    @Test
    fun correctLogLevelWarn() {
        val reflection = SystemOutReflection(LogAll)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.WARN, null, message, null)

        assertTrue { outputStreamCaptor.toString().startsWith("W/") }
    }


    private fun getLoggedTag(): String = outputStreamCaptor.toString()
        .substringAfter("/")
        .substringBefore(":")
        .trim()

    private fun getLoggedMessage() = outputStreamCaptor.toString()
        .substringAfter(":")
        .trim()

    @Test
    fun generateDebugTag() {
        val reflection = SystemOutReflection(LogAll, useDebugTags = true)
        val message = UUID.randomUUID().toString()
        reflection.log(EkhoLevel.ERROR, null, message, null)
        val tag = getLoggedTag()

        assertEquals("SystemOutReflectionTest", tag)
    }

    @Test
    fun generateDebugEkho() {
        val ekho = Ekho()
        val reflection = SystemOutReflection(LogAll, useDebugTags = true)
        ekho.addReflection(reflection)
        val message = UUID.randomUUID().toString()
        ekho.log(EkhoLevel.ERROR, null, null, message, null)
        val tag = getLoggedTag()

        assertEquals("SystemOutReflectionTest", tag)
    }

    class MyInnerClass {
        fun log(reflection: SystemOutReflection, message: String) {
            reflection.log(EkhoLevel.ERROR, null, message, null)
        }
    }


    @Test
    fun generateDebugTagOnInnerClass() {
        val reflection = SystemOutReflection(LogAll, useDebugTags = true)
        val message = UUID.randomUUID().toString()
        MyInnerClass().log(reflection, message)

        val tag = getLoggedTag()

        assertEquals("SystemOutReflectionTest", tag)
    }

    @Test
    fun doNotGenerateDebugTag() {
        val message = UUID.randomUUID().toString()
        val logTarget = SystemOutReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, null, message, null)

        val tag = getLoggedTag()

        assertEquals(SystemOutReflection.DEFAULT_TAG, tag)
    }

    @Test
    fun correctTag() {
        val tag = "my_tag"
        val message = UUID.randomUUID().toString()
        val logTarget = SystemOutReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, message, null)

        val printedTag = getLoggedTag()

        assertEquals(printedTag, tag)
    }

    @Test
    fun defaultTag() {
        val message = UUID.randomUUID().toString()
        val logTarget = SystemOutReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, null, message, null)

        val tag = getLoggedTag()

        assertEquals(SystemOutReflection.DEFAULT_TAG, tag)
    }

    @Test
    fun correctMessage() {
        val message = "My test message"
        val logTarget = SystemOutReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, null, message, null)

        val printedMessage = getLoggedMessage()

        assertEquals(message, printedMessage)
    }

    @Test
    fun correctThrowable() {
        val tag = UUID.randomUUID().toString()
        val logTarget = SystemOutReflection(LogAll)
        logTarget.log(EkhoLevel.ERROR, tag, null, NullPointerException())

        val printedMessage = getLoggedMessage()

        assertTrue(
            printedMessage.contains(NullPointerException::class.java.name),
            "No exception class in log message"
        )
    }

    @Test
    fun correctMessageAndThrowable() {
        val tag = UUID.randomUUID().toString()
        val logTarget = SystemOutReflection(LogAll)
        val message = "My test message"
        logTarget.log(EkhoLevel.ERROR, tag, message, NullPointerException())

        val printedMessage = getLoggedMessage()

        assertTrue(printedMessage.startsWith(message), "Message is not logged")
        assertTrue(
            printedMessage.contains(NullPointerException::class.java.name),
            "Throwable is not logged"
        )
    }
}