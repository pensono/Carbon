package org.carbon.syntax

import org.carbon.runtime.*
import org.carbon.runtime.Function

// Parameters is a list of string. Once typing is introduced, they'll also need to contain that
class ConditionSyntax(val condition: CarbonSyntax, val trueBranch: CarbonSyntax, val falseBranch: CarbonSyntax) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject {
        return Condition(condition.link(scope), trueBranch.link(scope), falseBranch.link(scope))
    }
}