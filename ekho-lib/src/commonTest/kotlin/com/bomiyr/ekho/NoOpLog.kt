package com.bomiyr.ekho

open class NoOpLog : EkhoLog {
    override fun v(message: String) {}
    override fun v(lazyMessage: () -> String) {}
    override fun v(error: Throwable) {}
    override fun v(error: Throwable, message: String) {}
    override fun v(error: Throwable, lazyMessage: () -> String) {}

    override fun d(message: String) {}
    override fun d(lazyMessage: () -> String) {}
    override fun d(error: Throwable) {}
    override fun d(error: Throwable, message: String) {}
    override fun d(error: Throwable, lazyMessage: () -> String) {}

    override fun i(message: String) {}
    override fun i(lazyMessage: () -> String) {}
    override fun i(error: Throwable) {}
    override fun i(error: Throwable, message: String) {}
    override fun i(error: Throwable, lazyMessage: () -> String) {}

    override fun w(message: String) {}
    override fun w(lazyMessage: () -> String) {}
    override fun w(error: Throwable) {}
    override fun w(error: Throwable, message: String) {}
    override fun w(error: Throwable, lazyMessage: () -> String) {}

    override fun e(message: String) {}
    override fun e(lazyMessage: () -> String) {}
    override fun e(error: Throwable) {}
    override fun e(error: Throwable, message: String) {}
    override fun e(error: Throwable, lazyMessage: () -> String) {}

    override fun wtf(message: String) {}
    override fun wtf(lazyMessage: () -> String) {}
    override fun wtf(error: Throwable) {}
    override fun wtf(error: Throwable, message: String) {}
    override fun wtf(error: Throwable, lazyMessage: () -> String) {}

    override fun log(
        level: EkhoLevel,
        tag: String?,
        error: Throwable?,
        message: String?,
        lazyMessage: (() -> String)?
    ) {
    }
}