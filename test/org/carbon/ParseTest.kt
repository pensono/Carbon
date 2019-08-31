package org.carbon

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParseTest {
    @ParameterizedTest
    @ValueSource(strings = [
        "Test=5", // No whitespace
        "Test = 5", // whitespace
        """
            Test = {
                Inner = 5
            }
        """,
        """
            Test = {
                Inner = 5
                AnotherInner = 6
            }
        """,
        "Test : Type",
        """
            Test = {
                Bound = 5
                Free : Int
            }
        """
    ])
    fun testParses(input: String) {
        assertNotNull(parseFile(input))
    }

    @ParameterizedTest
    @ValueSource(strings = ["."])
    fun testDoesNotParse(input: String) {
        assertNull(parseFile(input))
    }
}