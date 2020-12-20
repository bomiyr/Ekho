package com.bomiyr.ekho

import kotlin.jvm.Volatile

internal class EkhoReflectionsHolder : IEkhoReflectionsHolder {

    @Volatile
    var copyOnWriteList: List<EkhoReflection> = emptyList()

    override val size: Int
        get() = copyOnWriteList.size

    override fun add(reflection: EkhoReflection) {
        copyOnWriteList = copyOnWriteList + reflection
    }

    override fun add(reflections: List<EkhoReflection>) {
        copyOnWriteList = copyOnWriteList + reflections
    }

    override fun remove(reflection: EkhoReflection) {
        copyOnWriteList = copyOnWriteList - reflection
    }

    override fun remove(reflections: List<EkhoReflection>) {
        copyOnWriteList = copyOnWriteList - reflections
    }

    override fun clear() {
        copyOnWriteList = emptyList()
    }

    override fun forEach(code: (EkhoReflection) -> Unit) {
        copyOnWriteList.forEach {
            code(it)
        }
    }

    override fun all(): List<EkhoReflection> {
        return copyOnWriteList.toList()
    }
}