package org.carbon.syntax

import org.carbon.runtime.CarbonBoolean
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Function

// Parameters is a list of string. Once typing is introduced, they'll also need to contain that
class ConditionExpression(val condition: CarbonSyntax, val trueBranch: CarbonSyntax, val falseBranch: CarbonSyntax) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject {
        val conditionValue = condition.evaluate(scope) as CarbonBoolean
        val branch = if (conditionValue.value) trueBranch else falseBranch

        return branch.evaluate(scope)
    }
}