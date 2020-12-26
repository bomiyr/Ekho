package com.bomiyr.ekho

import java.io.PrintWriter
import java.io.StringWriter

public open class SystemOutReflection(
    override var filter: EkhoFilter,

    /**
     * Enable automatic tags calculation. Tag will be generated from calling class name.
     * It looks pretty, you don't need to provide tags for your messages at all, but be aware that
     * it is not free. Posting every log message will read current stacktrace.
     */
    public var useDebugTags: Boolean = false
) : EkhoReflection {

    private val skipClassesForDebugTag = listOf(
        SystemOutReflection::class.java.name,
        Ekho::class.java.name,
        EkhoReflectionsHolder::class.java.name
    )

    override fun log(level: EkhoLevel, tag: String?, message: String?, t: Throwable?) {
        prepareMessage(message, t)
            ?.let { resultMessage ->
                val resultTag = prepareTag(tag)
                println("${level.printed}/$resultTag: $resultMessage")
            }
    }

    private fun prepareTag(tag: String?): String? =
        tag ?: if (useDebugTags) {
            getTagFromStackTrace(skipClassesForDebugTag)
        } else {
            DEFAULT_TAG
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

    private fun getTagFromStackTrace(skipClassNames: List<String>): String? {

        val stackTrace = Throwable().stackTrace
        for (element in stackTrace) {
            val mainClass = element.className.substringBefore("$")
            if (!skipClassNames.contains(mainClass)) {
                return mainClass.substringAfterLast(".")
            }
        }
        return null
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
    }
}