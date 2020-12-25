package com.bomiyr.ekho

import android.os.Build
import android.util.Log
import com.bomiyr.ekho.jvmmessage.JvmMessagePrepare

public class AndroidLogReflection(
    override var filter: EkhoFilter = LogAll,

    /**
     * Enable automatic tags calculation. Tag will be generated from calling class name.
     * It looks pretty, you don't need to provide tags for your messages at all, but be aware that
     * it is not free. Posting every log message will read current stacktrace.
     */
    public var useDebugTags: Boolean = false
) : EkhoReflection {

    private val skipClassesForDebugTag = listOf(
        AndroidLogReflection::class.java.name,
        Ekho::class.java.name,
        EkhoReflectionsHolder::class.java.name
    )

    override fun log(level: EkhoLevel, tag: String?, message: String?, t: Throwable?) {
        JvmMessagePrepare.prepareMessage(message, t)
            ?.let { resultMessage ->
                val resultTag = prepareTag(tag)
                logFullMessage(level, resultTag, resultMessage)
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
            prepareTag(JvmMessagePrepare.getTagFromStackTrace(skipClassesForDebugTag))
        } else {
            DEFAULT_TAG
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
            EkhoLevel.ASSERT -> Log.wtf(resultTag, message)
            EkhoLevel.DEBUG -> Log.d(resultTag, message)
            EkhoLevel.ERROR -> Log.e(resultTag, message)
            EkhoLevel.INFO -> Log.i(resultTag, message)
            EkhoLevel.VERBOSE -> Log.v(resultTag, message)
            EkhoLevel.WARN -> Log.w(resultTag, message)
        }
    }


    public companion object {
        public const val DEFAULT_TAG: String = "Ekho"
        internal const val MAX_TAG_LENGTH = 23
        internal const val MAX_LOG_LENGTH = 4000
    }
}