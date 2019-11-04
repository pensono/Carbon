package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class EvaluateTest {
    @Test
    fun basicEval() {
        assertEquals(wrap(5), evaluate("Value = 5").getMember("Value"))
    }

    @Test
    fun identifier() {
        val value = evaluate("""
            Value = 5
            R = Value
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun composite() {
        val value = evaluate("""
            Value = { Inner = 5 }
        """).getMember("Value") as Composite
        assertEquals(wrap(5), value.getMember("Inner"))
    }

    @Test
    fun propertyAccessor() {
        val value = evaluate("""
            Value = { Inner = 5 }
            R = Value.Inner
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun trickyIdentifiers() {
        val value = evaluate("""
            Value = { 
                Inner = 5,
                Other = Inner
            }
            R = Value.Other
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun multiLevel() {
        val value = evaluate("""
            Value = { 
                Outer = {
                    Inner = 5
                } 
            }
            R = Value.Outer.Inner
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun searchOuterScopes() {
        val value = evaluate("""
            TopLevel = 5
            Value = { 
                Inner = TopLevel
            }
            R = Value.Inner
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun makeComposite() {
        val value = evaluate("""
            Rectangle = {
                Width: Integer,
                Height: Integer,
                WidthAlias = Width,
                HeightAlias = Height,
            }
            R = Rectangle(4, 5)
        """).getMember("R") as Composite

        // Hmm not ideal that it's not a member directly... is this the correct implementation?
        assertEquals(wrap(4), value.getMember("WidthAlias"))
        assertEquals(wrap(5), value.getMember("HeightAlias"))
    }

    @Test
    fun functionCall() {
        val value = evaluate("""
            Id(A : Integer) = A
            R = Id(5)
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun doubleFunctionCall() {
        val value = evaluate("""
            PlusOne(A : Integer) = A + 1
            PlusTwo(B : Integer) = PlusOne(B + 1)
            R = PlusTwo(5)
        """).getMember("R")
        assertEquals(wrap(7), value)
    }

    @Test
    fun functionCanReferToOuterScope() {
        val value = evaluate("""
            OuterVar = 5
            Id(A : Integer) = OuterVar
            R = Id(3)
        """).getMember("R")
        assertEquals(wrap(5), value)
    }

    @Test
    fun conditional() {
        val exprs = evaluate("""
            Sign(A : Integer)
              | A < 0 = -1
              = 1
            PosCase = Sign(5)
            NegCase = Sign(-5)
        """)
        assertEquals(wrap(1), exprs.getMember("PosCase"))
        assertEquals(wrap(-1), exprs.getMember("NegCase"))
    }

    @Test
    fun recursion() {
        val exprs = evaluate("""
            Fib(A : Integer)
              | A < 1 = 0
              | A < 3 = 1
              = Fib(A - 1) + Fib (A - 2)
            R = Fib(10)
        """)
        assertEquals(wrap(55), exprs.getMember("R"))
    }
}