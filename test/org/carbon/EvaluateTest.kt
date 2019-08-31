package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EvaluateTest {
    @Test
    fun basicEval() {
        assertEquals(wrap(5), evaluate("Value = 5", "Value"))
    }

    @Test
    fun identifier() {
        val value = evaluate("""
            Value = 5
            R = Value
        """, "R")
        assertEquals(wrap(5), value)
    }

//    @Test
//    fun propertyAccessor() {
//        val value = evaluate("""
//            Value = { Inner = 5 }
//            R = Value.Inner
//        """, "Value") as Composite
//        assertEquals(wrap(5), value.values["Inner"])
//    }
}