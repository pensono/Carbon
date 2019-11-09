package org.carbon

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

class AnnotationTest {
    @Test
    fun memoize() {
        assertTimeoutPreemptively(Duration.ofMillis(500)) {
            val exprs = evaluate(
                """
                    #Memoize
                    Fib(A : Integer)
                      | A < 1 = 0
                      | A < 3 = 1
                      = Fib(A - 1) + Fib (A - 2)
                    R = Fib(50)
                """
            )
            assertEquals(wrap(12586269025), exprs.getMember("R"))
        }
    }
}