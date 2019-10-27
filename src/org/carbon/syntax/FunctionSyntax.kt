package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite
import org.carbon.runtime.Function

// Parameters is a list of string. Once typing is introduced, they'll also need to contain that
class FunctionSyntax(val parameters: List<String>, val body: CarbonSyntax) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject {
        return Function(parameters, body, scope)
    }
}