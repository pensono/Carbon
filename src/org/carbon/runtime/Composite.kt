package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

// No parameters for now
class Composite(val values: Map<String, CarbonSyntax>, val lexicalScope: CarbonObject) : CarbonObject() {
    private val evaluatedValues : MutableMap<String, CarbonObject> = mutableMapOf()

    override fun lookupName(name: String): CarbonObject? = getMember(name) ?: lexicalScope.lookupName(name)
    override fun getMember(name: String): CarbonObject? {
        if (evaluatedValues.containsKey(name)) {
            return evaluatedValues.getValue(name)
        } else {
            // Lazy evaluation. Let's see how this works out
            val evaluatedValue = values[name]?.evaluate(this) ?: return null
            evaluatedValues[name] = evaluatedValue
            return evaluatedValue
        }
    }

    override fun toString(): String =
        "{ ${values.map { entry -> "${entry.key} = ${entry.value}" }.joinToString(", ")} }"
}