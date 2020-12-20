package com.bomiyr.ekho

open class NoOpReflectionsHolder : IEkhoReflectionsHolder {
    override val size: Int = 0

    override fun add(reflection: EkhoReflection) {}
    override fun add(reflections: List<EkhoReflection>) {}
    override fun remove(reflection: EkhoReflection) {}
    override fun remove(reflections: List<EkhoReflection>) {}
    override fun clear() {}
    override fun forEach(code: (EkhoReflection) -> Unit) {}
    override fun all(): List<EkhoReflection> = emptyList()

}