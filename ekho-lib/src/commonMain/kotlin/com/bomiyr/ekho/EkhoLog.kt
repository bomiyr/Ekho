package com.bomiyr.ekho

public interface EkhoLog {
    /**
     * Log a verbose message.
     */
    public fun v(message: String)

    /**
     * Log a verbose lazy message.
     */
    public fun v(lazyMessage: () -> String)

    /**
     * Log a verbose throwable.
     */
    public fun v(error: Throwable)

    /**
     * Log a verbose throwable with additional message.
     */
    public fun v(error: Throwable, message: String)

    /**
     * Log a verbose throwable with additional lazy message.
     */
    public fun v(error: Throwable, lazyMessage: () -> String)

    /**
     * Log a debug message.
     */
    public fun d(message: String)

    /**
     * Log a debug lazy message.
     */
    public fun d(lazyMessage: () -> String)

    /**
     * Log a debug throwable.
     */
    public fun d(error: Throwable)

    /**
     * Log a debug throwable with additional message.
     */
    public fun d(error: Throwable, message: String)

    /**
     * Log a debug throwable with additional lazy message.
     */
    public fun d(error: Throwable, lazyMessage: () -> String)

    /**
     * Log an info message.
     */
    public fun i(message: String)

    /**
     * Log an info lazy message.
     */
    public fun i(lazyMessage: () -> String)

    /**
     * Log an info throwable.
     */
    public fun i(error: Throwable)

    /**
     * Log an info throwable with additional message.
     */
    public fun i(error: Throwable, message: String)

    /**
     * Log an info throwable with additional lazy message.
     */
    public fun i(error: Throwable, lazyMessage: () -> String)

    /**
     * Log a warning message.
     */
    public fun w(message: String)

    /**
     * Log a warning lazy message.
     */
    public fun w(lazyMessage: () -> String)

    /**
     * Log a warning throwable.
     */
    public fun w(error: Throwable)

    /**
     * Log a warning throwable with additional message.
     */
    public fun w(error: Throwable, message: String)

    /**
     * Log a warning throwable with additional lazy message.
     */
    public fun w(error: Throwable, lazyMessage: () -> String)

    /**
     * Log an error message.
     */
    public fun e(message: String)

    /**
     * Log an error lazy message.
     */
    public fun e(lazyMessage: () -> String)

    /**
     * Log an error throwable.
     */
    public fun e(error: Throwable)

    /**
     * Log an error throwable with additional message.
     */
    public fun e(error: Throwable, message: String)

    /**
     * Log an error throwable with additional lazy message.
     */
    public fun e(error: Throwable, lazyMessage: () -> String)

    /**
     * Log an assert message.
     */
    public fun wtf(message: String)

    /**
     * Log an assert lazy message.
     */
    public fun wtf(lazyMessage: () -> String)

    /**
     * Log an assert throwable.
     */
    public fun wtf(error: Throwable)

    /**
     * Log an assert throwable with additional message.
     */
    public fun wtf(error: Throwable, message: String)

    /**
     * Log an assert throwable with additional lazy message.
     */
    public fun wtf(error: Throwable, lazyMessage: () -> String)

    /**
     * Low level log method. Should not be used in common case. All shortcut log methods will call this one at the end.
     * In default implementation lazyMessage will be ignored if message is not null
     */
    public fun log(
        level: EkhoLevel,
        tag: String?,
        error: Throwable?,
        message: String?,
        lazyMessage: (() -> String)?
    )
}