package org.carbon.intrepreter

import org.carbon.evaluate
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringTest {
    @Test
    fun basicTest() {
        val result = evaluate(
            """
            A = "Hello" 
        """
        )
        assertEquals(wrap("Hello"), result.getMember("A"))
    }

    @Test
    fun spaces() {
        val result = evaluate(
            """
            A = "  Hel lo  " 
        """
        )
        assertEquals(wrap("  Hel lo  "), result.getMember("A"))
    }

    @Test
    fun emptyString() {
        val result = evaluate(
            """
            A = "" 
        """
        )
        assertEquals(wrap(""), result.getMember("A"))
    }

    @Test
    fun concatenateTest() {
        val result = evaluate(
            """
            A = "Hello " + "there"
        """
        )
        assertEquals(wrap("Hello there"), result.getMember("A"))
    }

    @Test
    fun lengthTest() {
        val result = evaluate(
            """
            A = "Hello".Length
        """
        )
        assertEquals(wrap(5), result.getMember("A"))
    }

    // TODO Full UTF-8 support
}