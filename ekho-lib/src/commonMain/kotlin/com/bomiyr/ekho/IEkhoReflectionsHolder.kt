package com.bomiyr.ekho

internal interface IEkhoReflectionsHolder {
    val size: Int
    fun add(reflection: EkhoReflection)
    fun add(reflections: List<EkhoReflection>)
    fun remove(reflection: EkhoReflection)
    fun remove(reflections: List<EkhoReflection>)
    fun clear()
    fun forEach(code: (EkhoReflection) -> Unit)
    fun all(): List<EkhoReflection>
}