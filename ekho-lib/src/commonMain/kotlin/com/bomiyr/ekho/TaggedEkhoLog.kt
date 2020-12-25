package com.bomiyr.ekho

internal class TaggedEkhoLog(
    private val ekhoLog: EkhoLog,
    private val tag: String
) : EkhoLog {

    override fun v(message: String) {
        ekhoLog.log(EkhoLevel.VERBOSE, tag, error = null, message, lazyMessage = null)
    }

    override fun v(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.VERBOSE, tag, error = null, message = null, lazyMessage)
    }

    override fun v(error: Throwable) {
        ekhoLog.log(EkhoLevel.VERBOSE, tag, error, message = null, lazyMessage = null)
    }

    override fun v(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.VERBOSE, tag, error, message, lazyMessage = null)
    }

    override fun v(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.VERBOSE, tag, error, message = null, lazyMessage)
    }

    override fun d(message: String) {
        ekhoLog.log(EkhoLevel.DEBUG, tag, error = null, message, lazyMessage = null)
    }

    override fun d(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.DEBUG, tag, error = null, message = null, lazyMessage)
    }

    override fun d(error: Throwable) {
        ekhoLog.log(EkhoLevel.DEBUG, tag, error, message = null, lazyMessage = null)
    }

    override fun d(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.DEBUG, tag, error, message, lazyMessage = null)
    }

    override fun d(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.DEBUG, tag, error, message = null, lazyMessage)
    }

    override fun i(message: String) {
        ekhoLog.log(EkhoLevel.INFO, tag, error = null, message, lazyMessage = null)
    }

    override fun i(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.INFO, tag, error = null, message = null, lazyMessage)
    }

    override fun i(error: Throwable) {
        ekhoLog.log(EkhoLevel.INFO, tag, error, message = null, lazyMessage = null)
    }

    override fun i(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.INFO, tag, error, message, lazyMessage = null)
    }

    override fun i(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.INFO, tag, error, message = null, lazyMessage)
    }

    override fun w(message: String) {
        ekhoLog.log(EkhoLevel.WARN, tag, error = null, message, lazyMessage = null)
    }

    override fun w(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.WARN, tag, error = null, message = null, lazyMessage)
    }

    override fun w(error: Throwable) {
        ekhoLog.log(EkhoLevel.WARN, tag, error, message = null, lazyMessage = null)
    }

    override fun w(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.WARN, tag, error, message, lazyMessage = null)
    }

    override fun w(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.WARN, tag, error, message = null, lazyMessage)
    }

    override fun e(message: String) {
        ekhoLog.log(EkhoLevel.ERROR, tag, error = null, message, lazyMessage = null)
    }

    override fun e(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.ERROR, tag, error = null, message = null, lazyMessage)
    }

    override fun e(error: Throwable) {
        ekhoLog.log(EkhoLevel.ERROR, tag, error, message = null, lazyMessage = null)
    }

    override fun e(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.ERROR, tag, error, message, lazyMessage = null)
    }

    override fun e(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.ERROR, tag, error, message = null, lazyMessage)
    }

    override fun wtf(message: String) {
        ekhoLog.log(EkhoLevel.ASSERT, tag, error = null, message, lazyMessage = null)
    }

    override fun wtf(lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.ASSERT, tag, error = null, message = null, lazyMessage)
    }

    override fun wtf(error: Throwable) {
        ekhoLog.log(EkhoLevel.ASSERT, tag, error, message = null, lazyMessage = null)
    }

    override fun wtf(error: Throwable, message: String) {
        ekhoLog.log(EkhoLevel.ASSERT, tag, error, message, lazyMessage = null)
    }

    override fun wtf(error: Throwable, lazyMessage: () -> String) {
        ekhoLog.log(EkhoLevel.ASSERT, tag, error, message = null, lazyMessage)
    }

    override fun log(
        level: EkhoLevel,
        tag: String?,
        error: Throwable?,
        message: String?,
        lazyMessage: (() -> String)?
    ) {
        ekhoLog.log(level, tag, error, message, lazyMessage)
    }
}
