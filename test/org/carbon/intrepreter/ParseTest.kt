package org.carbon.intrepreter

import org.carbon.parseFile
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParseTest {
    @ParameterizedTest
    @ValueSource(strings = [
        "Test=5", // No whitespace
        "Test = 5", // whitespace
        "Test=-5", // Negative
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
        "Test = { A, B }",
        """
            Test = {
                A:Int, 
                B:Int
            }
        """,
        "Test : Type",
        """
            Test = {
                Bound = 5
                Free : Int
            }
        """,
        "A = 6" // Single letter
    ])
    fun basicParses(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        // Function calls
        "Shape = Square(4)",
        "Shape = Rectangle(4,5)",
        // Function definition
        "Square(Size:Integer) = Rectangle(Size, Size)",
        // Implicit name
        "Double(Integer) = Integer",
        // for good measure
        "Square(Size:Integer) = 4"
    ])
    fun parameters(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        "Val = 1 + 1",
        "Val = 1 + 1 * 7",
        """
            Val = 1 + 1
            Another = 2 - 2
        """,
        "Val = 1 < 2"
    ])
    fun operators(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        """
            Test = "Hello"
        """,
        """
            Empty = ""
        """
    ])
    fun stringLiterals(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        """
            // Comment
            Val = 1 + 1 // Another comment
            Another = 2 - 2
        """
    ])
    fun comments(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        """
            Sign(A : Integer)
                | A < 0 = -1
                = 1
        """
    ])
    fun conditional(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        """
            #TestAnnotation
            Const(A : Integer) = 1
        """
    ])
    fun annotation(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = [
        "R = []",
        "R = [1]",
        "R = [1,2,3]",
        "R = [1,2,3,]",
        """
            R = [
                ]
        """,
        """
            R = [
                    1
                ]
        """,
        """
            R = [
                    1,
                    2,
                    3
                ]
        """,
        """
            R = [
                    1,
                    2,
                    3,
                ]
        """
    ])
    fun list(input: String) = parseTest(input)

    @ParameterizedTest
    @ValueSource(strings = ["."])
    fun testDoesNotParse(input: String) = parseFailTest(input)

    private fun parseTest(input: String) {
        assertNotNull(parseFile(input))
    }

    private fun parseFailTest(input: String) {
        assertNull(parseFile(input))
    }
}