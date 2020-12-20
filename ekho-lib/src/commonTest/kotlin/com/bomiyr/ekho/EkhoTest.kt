package com.bomiyr.ekho

import kotlin.test.*

class EkhoTest {
    @Test
    fun getReflectionsCountSizeCalledOnReflectionsHolder() {
        var sizeCalled = false

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override val size: Int
                get() {
                    sizeCalled = true
                    return 0
                }
        }
        Ekho(reflectionsHolder).reflectionsCount
        assertTrue(sizeCalled, "Size property not called from reflectionsHolder")
    }

    @Test
    fun getReflectionsCountIsEqualToReflectionsHolderSize() {
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            var internalSize = 0
            override val size: Int
                get() {
                    return internalSize
                }
        }
        val ekho = Ekho(reflectionsHolder)
        assertEquals(
            reflectionsHolder.internalSize,
            ekho.reflectionsCount,
            "Returned sizes are not equal"
        )

        reflectionsHolder.internalSize = 1
        assertEquals(
            reflectionsHolder.internalSize,
            ekho.reflectionsCount,
            "Returned sizes are not equal"
        )
    }

    @Test
    fun addReflectionAddCalledOnReflectionsHolder() {
        var addMethodCalled = false

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun add(reflection: EkhoReflection) {
                addMethodCalled = true
            }
        }

        val ekho = Ekho(reflectionsHolder)
        ekho.addReflection(NoOpReflection())
        assertTrue(addMethodCalled, "Add method not called on reflectionsHolder")
    }

    @Test
    fun addReflectionsPassesToReflectionsHolder() {
        val reflection1 = NoOpReflection()
        val reflection2 = NoOpReflection()

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun add(reflections: List<EkhoReflection>) {
                assertEquals(2, reflections.size, "Count of added reflections not the same")
                assertEquals(reflection1, reflections[0], "Reflections not the same")
                assertEquals(reflection2, reflections[1], "Reflections not the same")
            }
        }

        val ekho = Ekho(reflectionsHolder)
        ekho.addReflections(listOf(reflection1, reflection2))
    }

    @Test
    fun removeReflectionRemoveCalledOnReflectionsHolder() {
        var removeMethodCalled = false

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun remove(reflection: EkhoReflection) {
                removeMethodCalled = true
            }
        }

        val ekho = Ekho(reflectionsHolder)
        ekho.removeReflection(NoOpReflection())
        assertTrue(removeMethodCalled, "Remove method not called on reflectionsHolder")
    }

    @Test
    fun removeReflectionsPassesToReflectionsHolder() {
        val reflection1 = NoOpReflection()
        val reflection2 = NoOpReflection()

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun remove(reflections: List<EkhoReflection>) {
                assertEquals(2, reflections.size, "Count of removed reflections not the same")
                assertEquals(reflection1, reflections[0], "Reflections not the same")
                assertEquals(reflection2, reflections[1], "Reflections not the same")
            }
        }

        val ekho = Ekho(reflectionsHolder)
        ekho.removeReflections(listOf(reflection1, reflection2))
    }

    @Test
    fun removeAllReflections() {
        var clearMethodCalled = false

        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun clear() {
                clearMethodCalled = true
            }
        }

        val ekho = Ekho(reflectionsHolder)
        ekho.removeAllReflections()
        assertTrue(clearMethodCalled, "Clear method not called on reflectionsHolder")
    }

    @Test
    fun tagged() {
        val reflectionsHolder = NoOpReflectionsHolder()
        val ekho = Ekho(reflectionsHolder)
        val tagged = ekho.tagged("tag")
        assertNotEquals(ekho, tagged, "Returned the same instance")
        assertTrue(tagged is TaggedEkhoLog, "Returned not tagged instance")
    }

    @Test
    fun logForeachCalledOnReflectionsHolder() {
        var foreachCalled = false
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun forEach(code: (EkhoReflection) -> Unit) {
                foreachCalled = true
            }
        }
        val ekho = Ekho(reflectionsHolder)
        ekho.log(Ekho.ASSERT, "tag1", Throwable(), null) {
            "message1"
        }
        assertTrue(foreachCalled, "Foreach not called on reflectionsHolder")
    }

    @Test
    fun logIsLoggableCalledOnReflection() {

        var isLoggableCalled = false

        val reflection = object : NoOpReflection() {
            override var filter: EkhoFilter = object : EkhoFilter {
                override fun isLoggable(level: EkhoLevel, tag: String?): Boolean {
                    isLoggableCalled = true
                    return false
                }
            }
        }
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun forEach(code: (EkhoReflection) -> Unit) {
                code(reflection)
            }
        }
        val ekho = Ekho(reflectionsHolder)
        ekho.log(Ekho.ASSERT, "tag1", Throwable(), null) {
            "message1"
        }
        assertTrue(isLoggableCalled, "Is loggable not called on reflection")
    }

    @Test
    fun logNotCalledWhenNotLoggable() {
        var logCalled = false
        val reflection = object : NoOpReflection() {

            override var filter: EkhoFilter = NoLogs

            override fun log(
                level: EkhoLevel,
                tag: String?,
                message: String?,
                t: Throwable?
            ) {
                logCalled = true
            }
        }
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun forEach(code: (EkhoReflection) -> Unit) {
                code(reflection)
            }
        }
        val ekho = Ekho(reflectionsHolder)
        ekho.log(Ekho.ASSERT, "tag1", Throwable(), null) {
            "message1"
        }
        assertFalse(logCalled, "Log is called on reflection when should not")
    }

    @Test
    fun logCalledWhenLoggable() {
        var logCalled = false
        val reflection = object : NoOpReflection() {

            override var filter: EkhoFilter = LogAll

            override fun log(
                level: EkhoLevel,
                tag: String?,
                message: String?,
                t: Throwable?
            ) {
                logCalled = true
            }
        }
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun forEach(code: (EkhoReflection) -> Unit) {
                code(reflection)
            }
        }
        val ekho = Ekho(reflectionsHolder)
        ekho.log(Ekho.ASSERT, "tag1", Throwable(), null) {
            "message1"
        }
        assertTrue(logCalled, "Log not called on reflection when it should")
    }


    @Test
    fun logCorrectData() {

        var testPriority = Ekho.ASSERT
        var testTag = "tag1"
        var testMessage = "message1"
        var testThrowable = Throwable()

        checkLoggedDataCorrect(testPriority, testTag, testMessage, testThrowable) {
            it.log(testPriority, testTag, testThrowable, null) {
                testMessage
            }
        }

        checkLoggedDataCorrect(testPriority, testTag, testMessage, testThrowable) {
            it.log(testPriority, testTag, testThrowable, testMessage, null)
        }

        testPriority = Ekho.WARN
        testTag = "tag2"
        testMessage = "message2"
        testThrowable = Throwable()

        checkLoggedDataCorrect(testPriority, testTag, testMessage, testThrowable) {
            it.log(testPriority, testTag, testThrowable, null) {
                testMessage
            }
        }
        checkLoggedDataCorrect(testPriority, testTag, testMessage, testThrowable) {
            it.log(testPriority, testTag, testThrowable, testMessage, null)
        }
    }

    private fun checkLoggedDataCorrect(
        testLevel: EkhoLevel,
        testTag: String?,
        testMessage: String?,
        testThrowable: Throwable?,
        loggingCode: (Ekho) -> Unit
    ) {
        val reflection = object : NoOpReflection() {

            override var filter: EkhoFilter = LogAll

            override fun log(
                level: EkhoLevel,
                tag: String?,
                message: String?,
                t: Throwable?
            ) {
                assertEquals(testLevel, level, "Priority is not the same")
                assertEquals(testTag, tag, "Tag is not the same")
                assertEquals(testMessage, message, "Message is not the same")
                assertEquals(testThrowable, t, "Throwable is not the same")
            }
        }
        val reflectionsHolder = object : NoOpReflectionsHolder() {
            override fun forEach(code: (EkhoReflection) -> Unit) {
                code(reflection)
            }
        }
        val ekho = Ekho(reflectionsHolder)
        loggingCode(ekho)
    }

    @Test
    fun vError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.VERBOSE, null, null, error) {
            it.v(error)
        }
    }

    @Test
    fun vMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.VERBOSE, null, message, null) {
            it.v(message)
        }
    }

    @Test
    fun vLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.VERBOSE, null, message, null) {
            it.v { message }
        }
    }

    @Test
    fun vErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.VERBOSE, null, message, error) {
            it.v(error, message)
        }
    }

    @Test
    fun vErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.VERBOSE, null, message, error) {
            it.v(error) { message }
        }
    }

    @Test
    fun dError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.DEBUG, null, null, error) {
            it.d(error)
        }
    }

    @Test
    fun dMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.DEBUG, null, message, null) {
            it.d(message)
        }
    }

    @Test
    fun dLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.DEBUG, null, message, null) {
            it.d { message }
        }
    }

    @Test
    fun dErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.DEBUG, null, message, error) {
            it.d(error, message)
        }
    }

    @Test
    fun dErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.DEBUG, null, message, error) {
            it.d(error) { message }
        }
    }

    @Test
    fun iError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.INFO, null, null, error) {
            it.i(error)
        }
    }

    @Test
    fun iMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.INFO, null, message, null) {
            it.i(message)
        }
    }

    @Test
    fun iLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.INFO, null, message, null) {
            it.i { message }
        }
    }

    @Test
    fun iErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.INFO, null, message, error) {
            it.i(error, message)
        }
    }

    @Test
    fun iErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.INFO, null, message, error) {
            it.i(error) { message }
        }
    }

    @Test
    fun wError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.WARN, null, null, error) {
            it.w(error)
        }
    }

    @Test
    fun wMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.WARN, null, message, null) {
            it.w(message)
        }
    }

    @Test
    fun wLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.WARN, null, message, null) {
            it.w { message }
        }
    }

    @Test
    fun wErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.WARN, null, message, error) {
            it.w(error, message)
        }
    }

    @Test
    fun wErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.WARN, null, message, error) {
            it.w(error) { message }
        }
    }

    @Test
    fun eError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.ERROR, null, null, error) {
            it.e(error)
        }
    }

    @Test
    fun eMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ERROR, null, message, null) {
            it.e(message)
        }
    }

    @Test
    fun eLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ERROR, null, message, null) {
            it.e { message }
        }
    }

    @Test
    fun eErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ERROR, null, message, error) {
            it.e(error, message)
        }
    }

    @Test
    fun eErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ERROR, null, message, error) {
            it.e(error) { message }
        }
    }

    @Test
    fun wtfError() {
        val error = Throwable()
        checkLoggedDataCorrect(Ekho.ASSERT, null, null, error) {
            it.wtf(error)
        }
    }

    @Test
    fun wtfMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ASSERT, null, message, null) {
            it.wtf(message)
        }
    }

    @Test
    fun wtfLazyMessage() {
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ASSERT, null, message, null) {
            it.wtf { message }
        }
    }

    @Test
    fun wtfErrorAndMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ASSERT, null, message, error) {
            it.wtf(error, message)
        }
    }

    @Test
    fun wtfErrorAndLazyMessage() {
        val error = Throwable()
        val message = "some message"
        checkLoggedDataCorrect(Ekho.ASSERT, null, message, error) {
            it.wtf(error) { message }
        }
    }
}