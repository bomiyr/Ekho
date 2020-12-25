package com.bomiyr.ekho

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

const val testMessage = "Ekho log message to test"

class TaggedEkhoTest {
    @Test
    fun vMessage() {
        testLogMessage(EkhoLevel.VERBOSE, testMessage) { logger ->
            logger.v(testMessage)
        }
    }

    @Test
    fun vLazyMessage() {
        testLogLazyMessage(EkhoLevel.VERBOSE, testMessage) { logger ->
            logger.v { testMessage }
        }
    }

    @Test
    fun vError() {
        val testError = Throwable()
        testLogError(EkhoLevel.VERBOSE, testError) { logger ->
            logger.v(testError)
        }
    }

    @Test
    fun vMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.VERBOSE, testMessage, testError) { logger ->
            logger.v(testError, testMessage)
        }
    }

    @Test
    fun vLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.VERBOSE, testMessage, testError) { logger ->
            logger.v(testError) { testMessage }
        }
    }

    @Test
    fun dMessage() {
        testLogMessage(EkhoLevel.DEBUG, testMessage) { logger ->
            logger.d(testMessage)
        }
    }

    @Test
    fun dLazyMessage() {
        testLogLazyMessage(EkhoLevel.DEBUG, testMessage) { logger ->
            logger.d { testMessage }
        }
    }

    @Test
    fun dError() {
        val testError = Throwable()
        testLogError(EkhoLevel.DEBUG, testError) { logger ->
            logger.d(testError)
        }
    }

    @Test
    fun dMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.DEBUG, testMessage, testError) { logger ->
            logger.d(testError, testMessage)
        }
    }

    @Test
    fun dLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.DEBUG, testMessage, testError) { logger ->
            logger.d(testError) { testMessage }
        }
    }

    @Test
    fun iMessage() {
        testLogMessage(EkhoLevel.INFO, testMessage) { logger ->
            logger.i(testMessage)
        }
    }

    @Test
    fun iLazyMessage() {
        testLogLazyMessage(EkhoLevel.INFO, testMessage) { logger ->
            logger.i { testMessage }
        }
    }

    @Test
    fun iError() {
        val testError = Throwable()
        testLogError(EkhoLevel.INFO, testError) { logger ->
            logger.i(testError)
        }
    }

    @Test
    fun iMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.INFO, testMessage, testError) { logger ->
            logger.i(testError, testMessage)
        }
    }

    @Test
    fun iLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.INFO, testMessage, testError) { logger ->
            logger.i(testError) { testMessage }
        }
    }

    @Test
    fun wMessage() {
        testLogMessage(EkhoLevel.WARN, testMessage) { logger ->
            logger.w(testMessage)
        }
    }

    @Test
    fun wLazyMessage() {
        testLogLazyMessage(EkhoLevel.WARN, testMessage) { logger ->
            logger.w { testMessage }
        }
    }

    @Test
    fun wError() {
        val testError = Throwable()
        testLogError(EkhoLevel.WARN, testError) { logger ->
            logger.w(testError)
        }
    }

    @Test
    fun wMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.WARN, testMessage, testError) { logger ->
            logger.w(testError, testMessage)
        }
    }

    @Test
    fun wLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.WARN, testMessage, testError) { logger ->
            logger.w(testError) { testMessage }
        }
    }

    @Test
    fun eMessage() {
        testLogMessage(EkhoLevel.ERROR, testMessage) { logger ->
            logger.e(testMessage)
        }
    }

    @Test
    fun eLazyMessage() {
        testLogLazyMessage(EkhoLevel.ERROR, testMessage) { logger ->
            logger.e { testMessage }
        }
    }

    @Test
    fun eError() {
        val testError = Throwable()
        testLogError(EkhoLevel.ERROR, testError) { logger ->
            logger.e(testError)
        }
    }

    @Test
    fun eMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.ERROR, testMessage, testError) { logger ->
            logger.e(testError, testMessage)
        }
    }

    @Test
    fun eLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.ERROR, testMessage, testError) { logger ->
            logger.e(testError) { testMessage }
        }
    }

    @Test
    fun wtfMessage() {
        testLogMessage(EkhoLevel.ASSERT, testMessage) { logger ->
            logger.wtf(testMessage)
        }
    }

    @Test
    fun wtfLazyMessage() {
        testLogLazyMessage(EkhoLevel.ASSERT, testMessage) { logger ->
            logger.wtf { testMessage }
        }
    }

    @Test
    fun wtfError() {
        val testError = Throwable()
        testLogError(EkhoLevel.ASSERT, testError) { logger ->
            logger.wtf(testError)
        }
    }

    @Test
    fun wtfMessageAndError() {
        val testError = Throwable()
        testLogMessageAndError(EkhoLevel.ASSERT, testMessage, testError) { logger ->
            logger.wtf(testError, testMessage)
        }
    }

    @Test
    fun wtfLazyMessageAndError() {
        val testError = Throwable()
        testLogLazyMessageAndError(EkhoLevel.ASSERT, testMessage, testError) { logger ->
            logger.wtf(testError) { testMessage }
        }
    }

    private fun testLogMessage(
        expectedLevel: EkhoLevel,
        expectedMessage: String,
        action: (EkhoLog) -> Unit
    ) {
        val testTag = "MyTag"
        var methodCalled = false
        val logger = object : NoOpLog() {

            override fun log(
                level: EkhoLevel,
                tag: String?,
                error: Throwable?,
                message: String?,
                lazyMessage: (() -> String)?
            ) {
                assertEquals(expectedLevel, level, "Priorities are not the same")
                assertEquals(testTag, tag, "Tags are not the same")
                assertEquals(expectedMessage, message, "Messages are not the same")
                methodCalled = true
            }
        }
        val taggedEkho = TaggedEkhoLog(logger, testTag)
        action(taggedEkho)
        assertTrue(methodCalled, "Method is not called")
    }

    private fun testLogLazyMessage(
        expectedLevel: EkhoLevel,
        expectedMessage: String,
        action: (EkhoLog) -> Unit
    ) {
        val testTag = "MyTag"
        var methodCalled = false
        val logger = object : NoOpLog() {

            override fun log(
                level: EkhoLevel,
                tag: String?,
                error: Throwable?,
                message: String?,
                lazyMessage: (() -> String)?
            ) {
                assertEquals(expectedLevel, level, "Priorities are not the same")
                assertEquals(testTag, tag, "Tags are not the same")
                assertEquals(expectedMessage, lazyMessage?.invoke(), "Messages are not the same")
                methodCalled = true
            }
        }
        val taggedEkho = TaggedEkhoLog(logger, testTag)
        action(taggedEkho)
        assertTrue(methodCalled, "Method is not called")
    }

    private fun testLogError(
        expectedLevel: EkhoLevel,
        expectedError: Throwable,
        action: (EkhoLog) -> Unit
    ) {
        val testTag = "MyTag"
        var methodCalled = false
        val logger = object : NoOpLog() {
            override fun log(
                level: EkhoLevel,
                tag: String?,
                error: Throwable?,
                message: String?,
                lazyMessage: (() -> String)?
            ) {
                assertEquals(expectedLevel, level, "Priorities are not the same")
                assertEquals(testTag, tag, "Tags are not the same")
                assertEquals(expectedError, error, "Errors are not the same")
                methodCalled = true
            }
        }
        val taggedEkho = TaggedEkhoLog(logger, testTag)
        action(taggedEkho)
        assertTrue(methodCalled, "Method is not called")
    }

    private fun testLogMessageAndError(
        expectedLevel: EkhoLevel,
        expectedMessage: String,
        expectedError: Throwable,
        action: (EkhoLog) -> Unit
    ) {
        val testTag = "MyTag"
        var methodCalled = false
        val logger = object : NoOpLog() {
            override fun log(
                level: EkhoLevel,
                tag: String?,
                error: Throwable?,
                message: String?,
                lazyMessage: (() -> String)?
            ) {
                assertEquals(expectedLevel, level, "Priorities are not the same")
                assertEquals(testTag, tag, "Tags are not the same")
                assertEquals(expectedError, error, "Errors are not the same")
                assertEquals(expectedMessage, message, "Messages are not the same")
                methodCalled = true
            }
        }
        val taggedEkho = TaggedEkhoLog(logger, testTag)
        action(taggedEkho)
        assertTrue(methodCalled, "Method is not called")
    }

    private fun testLogLazyMessageAndError(
        expectedLevel: EkhoLevel,
        expectedMessage: String,
        expectedError: Throwable,
        action: (EkhoLog) -> Unit
    ) {
        val testTag = "MyTag"
        var methodCalled = false
        val logger = object : NoOpLog() {
            override fun log(
                level: EkhoLevel,
                tag: String?,
                error: Throwable?,
                message: String?,
                lazyMessage: (() -> String)?
            ) {
                assertEquals(expectedLevel, level, "Priorities are not the same")
                assertEquals(testTag, tag, "Tags are not the same")
                assertEquals(expectedError, error, "Errors are not the same")
                assertEquals(expectedMessage, lazyMessage?.invoke(), "Messages are not the same")
                methodCalled = true
            }
        }
        val taggedEkho = TaggedEkhoLog(logger, testTag)
        action(taggedEkho)
        assertTrue(methodCalled, "Method is not called")
    }
}