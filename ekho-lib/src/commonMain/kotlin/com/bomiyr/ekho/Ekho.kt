package com.bomiyr.ekho

public typealias EkhoLevel = Int

public class Ekho : IEkho, EkhoLog {
    private val reflectionsHolder: IEkhoReflectionsHolder

    @Suppress("unused")
    public constructor() {
        reflectionsHolder = EkhoReflectionsHolder()
    }

    @Suppress("unused")
    internal constructor(reflections: IEkhoReflectionsHolder) {
        this.reflectionsHolder = reflections
    }

    override val reflectionsCount: Int
        get() = reflectionsHolder.size

    override val reflections: List<EkhoReflection>
        get() = reflectionsHolder.all()

    override fun addReflection(reflection: EkhoReflection) {
        reflectionsHolder.add(reflection)
    }

    override fun addReflections(reflections: List<EkhoReflection>) {
        reflectionsHolder.add(reflections)
    }

    override fun removeReflection(reflection: EkhoReflection) {
        reflectionsHolder.remove(reflection)
    }

    override fun removeReflections(reflections: List<EkhoReflection>) {
        reflectionsHolder.remove(reflections)
    }

    override fun removeAllReflections() {
        reflectionsHolder.clear()
    }

    override fun tagged(tag: String): EkhoLog {
        return TaggedEkhoLog(this, tag)
    }

    public companion object {
        public const val VERBOSE: EkhoLevel = 2
        public const val DEBUG: EkhoLevel = 3
        public const val INFO: EkhoLevel = 4
        public const val WARN: EkhoLevel = 5
        public const val ERROR: EkhoLevel = 6
        public const val ASSERT: EkhoLevel = 7
    }

    override fun log(
        level: EkhoLevel,
        tag: String?,
        error: Throwable?,
        message: String?,
        lazyMessage: (() -> String)?
    ) {
        var messageValue: String? = message
        reflectionsHolder.forEach {
            if (it.filter.isLoggable(level, tag)) {
                if (messageValue == null && lazyMessage != null) {
                    messageValue = lazyMessage()
                }
                it.log(level, tag, messageValue, error)
            }
        }
    }

    override fun v(message: String) {
        log(VERBOSE, null, error = null, message, lazyMessage = null)
    }

    override fun v(lazyMessage: () -> String) {
        log(VERBOSE, tag = null, error = null, message = null, lazyMessage)
    }

    override fun v(error: Throwable) {
        log(VERBOSE, tag = null, error, message = null, lazyMessage = null)
    }

    override fun v(error: Throwable, message: String) {
        log(VERBOSE, tag = null, error, message, lazyMessage = null)
    }

    override fun v(error: Throwable, lazyMessage: () -> String) {
        log(VERBOSE, tag = null, error, message = null, lazyMessage)
    }

    override fun d(message: String) {
        log(DEBUG, tag = null, error = null, message, lazyMessage = null)
    }

    override fun d(lazyMessage: () -> String) {
        log(DEBUG, tag = null, error = null, message = null, lazyMessage)
    }

    override fun d(error: Throwable) {
        log(DEBUG, tag = null, error, message = null, lazyMessage = null)
    }

    override fun d(error: Throwable, message: String) {
        log(DEBUG, tag = null, error, message, lazyMessage = null)
    }

    override fun d(error: Throwable, lazyMessage: () -> String) {
        log(DEBUG, tag = null, error, message = null, lazyMessage)
    }

    override fun i(message: String) {
        log(INFO, tag = null, error = null, message, lazyMessage = null)
    }

    override fun i(lazyMessage: () -> String) {
        log(INFO, tag = null, error = null, message = null, lazyMessage)
    }

    override fun i(error: Throwable) {
        log(INFO, tag = null, error, message = null, lazyMessage = null)
    }

    override fun i(error: Throwable, message: String) {
        log(INFO, tag = null, error, message, lazyMessage = null)
    }

    override fun i(error: Throwable, lazyMessage: () -> String) {
        log(INFO, tag = null, error, message = null, lazyMessage)
    }

    override fun w(message: String) {
        log(WARN, tag = null, error = null, message, lazyMessage = null)
    }

    override fun w(lazyMessage: () -> String) {
        log(WARN, tag = null, error = null, message = null, lazyMessage)
    }

    override fun w(error: Throwable) {
        log(WARN, tag = null, error, message = null, lazyMessage = null)
    }

    override fun w(error: Throwable, message: String) {
        log(WARN, tag = null, error, message, lazyMessage = null)
    }

    override fun w(error: Throwable, lazyMessage: () -> String) {
        log(WARN, tag = null, error, message = null, lazyMessage)
    }

    override fun e(message: String) {
        log(ERROR, tag = null, error = null, message, lazyMessage = null)
    }

    override fun e(lazyMessage: () -> String) {
        log(ERROR, tag = null, error = null, message = null, lazyMessage)
    }

    override fun e(error: Throwable) {
        log(ERROR, tag = null, error, message = null, lazyMessage = null)
    }

    override fun e(error: Throwable, message: String) {
        log(ERROR, tag = null, error, message, lazyMessage = null)
    }

    override fun e(error: Throwable, lazyMessage: () -> String) {
        log(ERROR, tag = null, error, message = null, lazyMessage)
    }

    override fun wtf(message: String) {
        log(ASSERT, tag = null, error = null, message, lazyMessage = null)
    }

    override fun wtf(lazyMessage: () -> String) {
        log(ASSERT, tag = null, error = null, message = null, lazyMessage)
    }

    override fun wtf(error: Throwable) {
        log(ASSERT, tag = null, error, message = null, lazyMessage = null)
    }

    override fun wtf(error: Throwable, message: String) {
        log(ASSERT, tag = null, error, message, lazyMessage = null)
    }

    override fun wtf(error: Throwable, lazyMessage: () -> String) {
        log(ASSERT, tag = null, error, message = null, lazyMessage)
    }
}
