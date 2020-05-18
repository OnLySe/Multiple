package com.minew.navigation

import android.util.SparseArray
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.minew.navigation", appContext.packageName)
    }

    @Test
    fun testSparseArray() {
        val array = SparseArray<String>()
        array.put(1, "aa")
        array.put(1, "bdf")
        array.put(2, "e")
        array.put(3, "f")
        array.put(8, "re")
        array.put(3, "43")
        println(array.size())
        array.put(4, "32")
        array.put(5, "ew")
        array.put(6,"123456")
        println(array.size())
    }
}
