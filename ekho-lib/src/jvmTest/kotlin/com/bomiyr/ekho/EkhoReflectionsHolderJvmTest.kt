package com.bomiyr.ekho

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class EkhoReflectionsHolderJvmTest {

//    @Test
//    fun addReflectionsMultiThreaded() {
//
//        val reflectionsHolder = EkhoReflectionsHolder()
//
//        val times = 1000
//        runBlocking {
//            launch(IO) {
//                repeat(times) {
//                    reflectionsHolder.add(listOf(NoOpReflection()))
//                }
//            }
//            launch(IO) {
//                repeat(times) {
//                    reflectionsHolder.add(listOf(NoOpReflection()))
//                }
//            }
//        }
//        assertEquals(
//            times * 2,
//            reflectionsHolder.size,
//            "Unexpected reflections count after multithreaded add"
//        )
//    }

//    @Test
//    fun deleteReflectionsMultiThreaded() {
//
//        val reflectionsHolder = EkhoReflectionsHolder()
//
//        val count = 1000
//        val reflections = List(count * 2) { NoOpReflection() }
//
//        reflectionsHolder.add(reflections)
//
//        runBlocking {
//            launch(IO) {
//                reflections.take(count).forEach {
//                    reflectionsHolder.remove(listOf(it))
//                }
//            }
//            launch(IO) {
//                reflections.takeLast(count).forEach {
//                    reflectionsHolder.remove(listOf(it))
//                }
//            }
//        }
//        assertEquals(0, reflectionsHolder.size, "Unexpected reflections count after multithreaded remove")
//    }

    @Test
    fun foreachMultithreadedModifications() {

        val reflectionsHolder = EkhoReflectionsHolder()

        val reflections = List(5) { NoOpReflection() }
        reflectionsHolder.add(reflections)
        val times = 1000

        runBlocking {
            launch(IO) {
                repeat(times) {
                    val target = NoOpReflection()
                    reflectionsHolder.add(listOf(target))
                    reflectionsHolder.remove(listOf(target))
                }
            }
            launch(IO) {
                repeat(times) { _ ->
                    reflectionsHolder.forEach { it }
                }
            }
        }

        // No assert. it will crash if something wrong
    }
}