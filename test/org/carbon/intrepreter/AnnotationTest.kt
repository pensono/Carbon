package org.carbon.intrepreter

import org.carbon.evaluate
import org.carbon.runtime.CarbonInteger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.TimeUnit

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
        val exprs = evaluate(
            """
            #TailCall
            CountDown(A : Integer)
              | A < 1 = 0
              = CountDown(A-1)
            R = CountDown(100000)
        """
        )
        assertEquals(wrap(0), exprs.getMember("R"))
    }

    @Test
    fun tailRecursionMultiArg() {
        val exprs = evaluate(
            """    
            Factorial(N:Int) = FactorialHelper(N, 1)
            
            #TailCall
            FactorialHelper(N: Int, Total: Int) 
                | N < 2 = Total
                = FactorialHelper(N - 1, Total * N)
    
            R = Factorial(20)
        """
        )
        assertEquals(wrap(2432902008176640000), exprs.getMember("R"))
    }

    @Test
    @Tag("unreliable")
    fun sample() {
        val exprs = evaluate(
            """
            #Sample(200)
            Time = CurrentTime.UnixMillis
        """
        )

        val firstTime = unwrap(exprs.getMember("Time") as CarbonInteger)
        TimeUnit.MILLISECONDS.sleep(300)
        val secondTime = unwrap(exprs.getMember("Time") as CarbonInteger)

        // If the sampling happens every 100ms, then ideally the times would be 100ms apart.
        val difference = secondTime - firstTime
        assert(difference >= 200)
        assert(difference < 380)
    }
}