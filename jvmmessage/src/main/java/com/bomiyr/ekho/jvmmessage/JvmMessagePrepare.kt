package com.bomiyr.ekho.jvmmessage

import java.io.PrintWriter
import java.io.StringWriter

object JvmMessagePrepare {

    fun prepareMessage(message: String?, error: Throwable?): String? {
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

    fun getTagFromStackTrace(skipClassNames: List<String>): String? {

        val stackTrace = Throwable().stackTrace
        for (element in stackTrace) {
            val mainClass = element.className.substringBefore("$")
            if (!skipClassNames.contains(mainClass) &&
                mainClass != JvmMessagePrepare::class.java.name
            ) {
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
}