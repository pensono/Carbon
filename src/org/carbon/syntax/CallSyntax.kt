package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Function

class CallSyntax(val actualParameters: List<CarbonSyntax>, val base: CarbonSyntax) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject {
        val evaluatedBase = base.evaluate(scope) as Function
        val arguments = actualParameters.map { it.evaluate(scope) }

        return evaluatedBase.call(arguments)
    }

    override fun toString(): String = "$base(${actualParameters.map { it.toString() }.joinToString(", ")})"
}