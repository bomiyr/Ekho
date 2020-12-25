package com.bomiyr.ekho

import com.bomiyr.ekho.jvmmessage.JvmMessagePrepare

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
        JvmMessagePrepare.prepareMessage(message, t)
            ?.let { resultMessage ->
                val resultTag = prepareTag(tag)
                println("${level.printed}/$resultTag: $resultMessage")
            }
    }

    private fun prepareTag(tag: String?): String? =
        tag ?: if (useDebugTags) {
            JvmMessagePrepare.getTagFromStackTrace(skipClassesForDebugTag)
        } else {
            DEFAULT_TAG
        }

    public companion object {
        public const val DEFAULT_TAG: String = "Ekho"
    }
}