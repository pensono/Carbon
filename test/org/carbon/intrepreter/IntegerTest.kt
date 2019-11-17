package org.carbon.intrepreter

import org.carbon.evaluate
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class IntegerTest {
    @ParameterizedTest
    @ValueSource(strings = [
        "A = 2 + 3, B = 5",
        "A = 2 * 3, B = 6",
        "A = 2 - 3, B = -1"
    ])
    fun mathTests(program: String) {
        val result = evaluate(program)
        assertEquals(result.getMember("B"), result.getMember("A"))
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "A = 2 + 3 * 3, B = 11",
        "A = 2 * 3 + 3, B = 9"
    ])
    fun orderOfOperations(program: String) {
        val result = evaluate(program)
        assertEquals(result.getMember("B"), result.getMember("A"))
    }
}