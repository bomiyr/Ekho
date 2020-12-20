package com.bomiyr.ekho

open class NoOpReflection : EkhoReflection {
    override var filter: EkhoFilter = LogAll

    override fun log(level: EkhoLevel, tag: String?, message: String?, t: Throwable?) {}
}