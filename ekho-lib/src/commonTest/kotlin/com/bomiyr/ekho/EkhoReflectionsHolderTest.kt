package com.bomiyr.ekho

import kotlin.test.Test
import kotlin.test.assertEquals

class EkhoReflectionsHolderTest {

    @Test
    fun noReflectionsByDefault() {
        val reflectionsHolder = EkhoReflectionsHolder()
        assertEquals(0, reflectionsHolder.size, "Reflections size is not zero by default")
    }

    @Test
    fun addedReflectionsCountByOne() {
        val reflectionsHolder = EkhoReflectionsHolder()

        for (i in 1..5) {
            reflectionsHolder.add(listOf(NoOpReflection()))
            assertEquals(i, reflectionsHolder.size, "Unexpected reflections count after add")
        }
    }

    @Test
    fun addedReflectionsCountBySome() {
        val reflectionsHolder = EkhoReflectionsHolder()

        for (i in 3..9 step 3) {
            reflectionsHolder.add(
                listOf(
                    NoOpReflection(),
                    NoOpReflection(),
                    NoOpReflection()
                )
            )
            assertEquals(i, reflectionsHolder.size, "Unexpected reflections count after add")
        }
    }

    @Test
    fun removedReflectionsCountByOne() {
        val reflectionsHolder = EkhoReflectionsHolder()
        val reflections = listOf(
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection()
        )
        reflectionsHolder.add(reflections)

        for (i in reflections.size - 1 downTo 0) {
            val reflection = reflections[i]
            reflectionsHolder.remove(listOf(reflection))

            assertEquals(i, reflectionsHolder.size, "Unexpected reflections count after remove")
        }
    }

    @Test
    fun removedReflectionsCountBySome() {
        val reflectionsHolder = EkhoReflectionsHolder()
        val reflections = listOf(
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection()
        )
        reflectionsHolder.add(reflections)

        for (i in reflections.size - 3 downTo 0 step 3) {
            val reflection1 = reflections[i]
            val reflection2 = reflections[i + 1]
            val reflection3 = reflections[i + 2]
            reflectionsHolder.remove(listOf(reflection1, reflection2, reflection3))

            assertEquals(i, reflectionsHolder.size, "Unexpected reflections count after remove")
        }
    }

    @Test
    fun clear() {
        val reflectionsHolder = EkhoReflectionsHolder()
        val reflections = listOf(
            NoOpReflection(),
            NoOpReflection(),
            NoOpReflection(),
        )
        reflectionsHolder.add(reflections)

        reflectionsHolder.clear()

        assertEquals(0, reflectionsHolder.size, "Unexpected reflections count after clear")
    }

    @Test
    fun foreach() {
        val reflection1 = NoOpReflection()
        val reflection2 = NoOpReflection()

        val reflectionsHolder = EkhoReflectionsHolder()
        reflectionsHolder.add(listOf(reflection1, reflection2))

        var calledForReflection1 = 0
        var calledFOrReflection2 = 0

        val repeatsCount = 10

        repeat(repeatsCount) {
            reflectionsHolder.forEach {
                if (it === reflection1) {
                    calledForReflection1++
                } else if (it === reflection2) {
                    calledFOrReflection2++
                }
            }
        }

        assertEquals(calledForReflection1, calledFOrReflection2, "Reflections not called equally")
        assertEquals(
            repeatsCount,
            calledForReflection1,
            "Unexpected count of block execution in foreach"
        )
    }
}