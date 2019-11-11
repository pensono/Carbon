package org.carbon

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class ListTest {
    @Test
    fun basic() {
        val result = evaluate("A = [1, 2, 3]")
        assertEquals(wrap(1, 2, 3), result.getMember("A"))
    }

    @Test
    fun empty() {
        val result = evaluate("A = []")
        assertEquals(wrap(), result.getMember("A"))
    }

    @Test
    fun concatenate() {
        val result = evaluate("A = [1] + [2, 3]")
        assertEquals(wrap(1, 2, 3), result.getMember("A"))
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "A = [].Length, E = 0",
        "A = [2].Length, E = 1",
        "A = [1, 2, 3].Length, E = 3"
    ])
    fun count(program: String) {
        val result = evaluate(program)
        assertEquals(result.getMember("E"), result.getMember("A"))
    }

    // TODO map test
}