package com.bomiyr.ekho

public interface EkhoFilter {
    public fun isLoggable(level: EkhoLevel, tag: String?): Boolean
}

@Suppress("unused")
public object LogAll : EkhoFilter {
    override fun isLoggable(level: EkhoLevel, tag: String?): Boolean = true
}

@Suppress("unused")
public class MinLogLevel(
    @Suppress("MemberVisibilityCanBePrivate") public val minEkhoLevel: EkhoLevel
) : EkhoFilter {
    override fun isLoggable(level: EkhoLevel, tag: String?): Boolean = level >= minEkhoLevel
}

@Suppress("unused")
public object NoLogs : EkhoFilter {
    override fun isLoggable(level: EkhoLevel, tag: String?): Boolean = false
}