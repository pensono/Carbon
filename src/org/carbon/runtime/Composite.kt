package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

// No parameters for now
open class Composite(val values: Map<String, CarbonSyntax> = mapOf(), val lexicalScope: Composite? = null) : CarbonObject() {
    override fun evaluate(): CarbonObject = this

    private val evaluatedValues : MutableMap<String, CarbonObject> = mutableMapOf()
    private val inputs: Map<String, CarbonObject> = mutableMapOf()

    fun lookupName(name: String): CarbonObject? = getMember(name) ?: lexicalScope?.lookupName(name)
    open fun getMember(name: String): CarbonObject? {
        if (evaluatedValues.containsKey(name)) {
            return evaluatedValues.getValue(name)
        } else {
            // Lazy evaluation. Let's see how this works out
            val evaluatedValue = values[name]?.performLink(this) ?: return null
            evaluatedValues[name] = evaluatedValue
            return evaluatedValue
        }
    }

    override fun toString(): String =
        "{ ${values.map { entry -> "${entry.key} = ${entry.value}" }.joinToString(", ")} }"
}