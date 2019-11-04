package org.carbon.syntax

import org.carbon.runtime.*

// Parameters is a list of string. Once typing is introduced, they'll also need to contain that
class ConditionSyntax(val condition: CarbonObject, val trueBranch: CarbonObject, val falseBranch: CarbonObject) : CarbonSyntax() {
    override fun evaluate(scope: Composite): CarbonObject {
        val conditionValue = condition.evaluate(scope) as CarbonBoolean
        val branch = if (conditionValue.value) trueBranch else falseBranch

        // Call to evaluate needed here?
        return branch.evaluate(scope)
    }
}