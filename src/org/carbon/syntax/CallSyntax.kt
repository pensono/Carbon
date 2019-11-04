package org.carbon.syntax

import org.carbon.runtime.Callable
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class CallSyntax(val actualParameters: List<CarbonObject>, val base: CarbonObject) : CarbonSyntax() {
    override fun evaluate(scope: Composite): CarbonObject {
        val evaluatedBase = base.evaluate(scope) as Callable
        val arguments = actualParameters.map { it.evaluate(scope) }

        return evaluatedBase.apply(arguments)
    }

    override fun toString(): String = "$base(${actualParameters.map { it.toString() }.joinToString(", ")})"
}