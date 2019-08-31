package org.carbon

import org.carbon.runtime.CarbonInteger
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EvaluateTest {
    @Test
    fun basicEval() {
        val value = evaluate("Value = 5", "Value") as CarbonInteger
        assertEquals(value.value, 5)
    }
}