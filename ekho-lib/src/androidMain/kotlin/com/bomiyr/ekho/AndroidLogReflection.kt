package com.bomiyr.ekho

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

public class AndroidLogReflection(
    override var filter: EkhoFilter = LogAll,

    /**
     * Enable automatic tags calculation. Tag will be generated from calling class name.
     * It looks pretty, you don't need to provide tags for your messages at all, but be aware that
     * it is not free. Posting every log message will read current stacktrace.
     */
    public var useDebugTags: Boolean = false
) : EkhoReflection {

    override fun log(level: EkhoLevel, tag: String?, message: String?, t: Throwable?) {
        prepareMessage(message, t)
            ?.let { resultMessage ->
                val resultTag = prepareTag(tag)
                logFullMessage(level, resultTag, resultMessage)
            }
    }

    private fun prepareMessage(message: String?, error: Throwable?): String? {
        return if (message != null) {
            if (error != null) {
                "$message\n${getStackTraceString(error)}"
            } else {
                message
            }
        } else {
            if (error != null) {
                getStackTraceString(error)
            } else {
                null
            }
        }
    }

    private fun prepareTag(tag: String?): String =
        if (tag != null) {
            if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tag
            } else {
                tag.substring(0, MAX_TAG_LENGTH)
            }
        } else if (useDebugTags) {
            prepareTag(getTagFromStackTrace())
        } else {
            DEFAULT_TAG
        }

    private fun getTagFromStackTrace(): String {
        val skipForTag = listOf(
            AndroidLogReflection::class.java.name,
            Ekho::class.java.name,
            EkhoReflectionsHolder::class.java.name
        )
        return Throwable()
            .stackTrace
            .first {
                !skipForTag.contains(it.className)
            }
            .let {
                createStackElementTag(it)
            }
    }

    private fun createStackElementTag(element: StackTraceElement): String {
        return element.className
            .substringAfterLast('.')
            .substringBefore("$")
    }

    private fun logFullMessage(
        level: EkhoLevel,
        tag: String,
        message: String
    ) {
        if (message.length < MAX_LOG_LENGTH) {
            log(level, tag, message)
        } else {
            message
                .chunked(MAX_LOG_LENGTH)
                .forEach {
                    log(level, tag, it)
                }
        }
    }

    private fun log(
        level: EkhoLevel,
        resultTag: String,
        message: String
    ) {
        when (level) {
            Ekho.ASSERT -> Log.wtf(resultTag, message)
            Ekho.DEBUG -> Log.d(resultTag, message)
            Ekho.ERROR -> Log.e(resultTag, message)
            Ekho.INFO -> Log.i(resultTag, message)
            Ekho.VERBOSE -> Log.v(resultTag, message)
            Ekho.WARN -> Log.w(resultTag, message)
        }
    }

    private fun getStackTraceString(tr: Throwable): String {
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    public companion object {
        public const val DEFAULT_TAG: String = "Ekho"
        internal const val MAX_TAG_LENGTH = 23
        internal const val MAX_LOG_LENGTH = 4000
    }
}