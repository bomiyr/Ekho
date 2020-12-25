package com.bomiyr.ekho

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EkhoFilterTests {
    @Test
    fun logAll() {
        assertTrue(
            EkhoLevel.values().all { LogAll.isLoggable(it, null) },
            "Not all logs logged for LogAll filter"
        )
    }

    @Test
    fun minLogLevelVerbose() {
        val filter = MinLogLevel(EkhoLevel.VERBOSE)
        assertTrue(
            EkhoLevel.values().all { filter.isLoggable(it, null) },
            "Not all logs logged for MinLogLevel(VERBOSE) filter"
        )
    }

    @Test
    fun minLogLevelDebug() {
        val filter = MinLogLevel(EkhoLevel.DEBUG)

        assertFalse(
            filter.isLoggable(EkhoLevel.VERBOSE, null),
            "Message logged for VERBOSE when MinLogLevel(DEBUG)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.DEBUG, null),
            "Message not logged for DEBUG when MinLogLevel(DEBUG)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.INFO, null),
            "Message not logged for INFO when MinLogLevel(DEBUG)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.WARN, null),
            "Message not logged for WARN when MinLogLevel(DEBUG)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ERROR, null),
            "Message not logged for ERROR when MinLogLevel(DEBUG)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ASSERT, null),
            "Message not logged for ASSERT when MinLogLevel(DEBUG)"
        )
    }

    @Test
    fun minLogLevelInfo() {
        val filter = MinLogLevel(EkhoLevel.INFO)

        assertFalse(
            filter.isLoggable(EkhoLevel.VERBOSE, null),
            "Message logged for VERBOSE when MinLogLevel(INFO)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.DEBUG, null),
            "Message logged for DEBUG when MinLogLevel(INFO)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.INFO, null),
            "Message not logged for INFO when MinLogLevel(INFO)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.WARN, null),
            "Message not logged for WARN when MinLogLevel(INFO)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ERROR, null),
            "Message not logged for ERROR when MinLogLevel(INFO)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ASSERT, null),
            "Message not logged for ASSERT when MinLogLevel(INFO)"
        )
    }

    @Test
    fun minLogLevelWarn() {
        val filter = MinLogLevel(EkhoLevel.WARN)

        assertFalse(
            filter.isLoggable(EkhoLevel.VERBOSE, null),
            "Message logged for VERBOSE when MinLogLevel(WARN)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.DEBUG, null),
            "Message logged for DEBUG when MinLogLevel(WARN)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.INFO, null),
            "Message logged for INFO when MinLogLevel(WARN)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.WARN, null),
            "Message not logged for WARN when MinLogLevel(WARN)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ERROR, null),
            "Message not logged for ERROR when MinLogLevel(WARN)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ASSERT, null),
            "Message not logged for ASSERT when MinLogLevel(WARN)"
        )
    }

    @Test
    fun minLogLevelError() {
        val filter = MinLogLevel(EkhoLevel.ERROR)

        assertFalse(
            filter.isLoggable(EkhoLevel.VERBOSE, null),
            "Message logged for VERBOSE when MinLogLevel(ERROR)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.DEBUG, null),
            "Message logged for DEBUG when MinLogLevel(ERROR)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.INFO, null),
            "Message logged for INFO when MinLogLevel(ERROR)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.WARN, null),
            "Message logged for WARN when MinLogLevel(ERROR)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ERROR, null),
            "Message not logged for ERROR when MinLogLevel(ERROR)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ASSERT, null),
            "Message not logged for ASSERT when MinLogLevel(ERROR)"
        )
    }

    @Test
    fun minLogLevelAssert() {
        val filter = MinLogLevel(EkhoLevel.ASSERT)

        assertFalse(
            filter.isLoggable(EkhoLevel.VERBOSE, null),
            "Message logged for VERBOSE when MinLogLevel(ASSERT)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.DEBUG, null),
            "Message logged for DEBUG when MinLogLevel(ASSERT)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.INFO, null),
            "Message logged for INFO when MinLogLevel(ASSERT)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.WARN, null),
            "Message logged for WARN when MinLogLevel(ASSERT)"
        )
        assertFalse(
            filter.isLoggable(EkhoLevel.ERROR, null),
            "Message logged for ERROR when MinLogLevel(ASSERT)"
        )
        assertTrue(
            filter.isLoggable(EkhoLevel.ASSERT, null),
            "Message not logged for ASSERT when MinLogLevel(ASSERT)"
        )
    }

    @Test
    fun noLogs() {
        assertTrue(
            EkhoLevel.values().none { NoLogs.isLoggable(it, null) },
            "Something logged for NoLogs filter"
        )
    }
}