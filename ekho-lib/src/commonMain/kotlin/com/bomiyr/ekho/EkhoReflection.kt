package com.bomiyr.ekho

public interface EkhoReflection {
    public var filter: EkhoFilter
    public fun log(level: EkhoLevel, tag: String?, message: String?, t: Throwable?)
}
