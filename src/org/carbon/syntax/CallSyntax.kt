package org.carbon.syntax

import org.carbon.runtime.Callable
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class CallSyntax(val actualParameters: List<CarbonSyntax>, val base: CarbonSyntax) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject {
        val evaluatedBase = base.performLink(scope) as Callable
        val arguments = actualParameters.map { it.performLink(scope) }

        return evaluatedBase.apply(arguments)
    }

    override fun toString(): String = "$base(${actualParameters.map { it.toString() }.joinToString(", ")})"
}