package org.carbon.runtime

class Condition(val condition: CarbonObject, val trueBranch: CarbonObject, val falseBranch: CarbonObject) : CarbonObject() {
    override fun evaluate(): CarbonObject {
        val conditionValue = condition.evaluate() as CarbonBoolean
        val branch = if (conditionValue.value) trueBranch else falseBranch

        // Call to evaluate needed here?
        return branch.evaluate()
    }
}