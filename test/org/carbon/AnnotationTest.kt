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

    @Test
    fun tailRecursion() {
        val exprs = evaluate("""
            #TailCall
            CountDown(A : Integer)
              | A < 1 = 0
              = CountDown(A-1)
            R = CountDown(100000)
        """)
        kotlin.test.assertEquals(wrap(0), exprs.getMember("R"))
    }

    @Test
    fun tailRecursionMultiArg() {
        val exprs = evaluate("""    
            Factorial(N:Int) = FactorialHelper(N, 1)
            
            #TailCall
            FactorialHelper(N: Int, Total: Int) 
                | N < 2 = Total
                = FactorialHelper(N - 1, Total * N)
    
            R = Factorial(20)
        """)
        kotlin.test.assertEquals(wrap(2432902008176640000), exprs.getMember("R"))
    }
}