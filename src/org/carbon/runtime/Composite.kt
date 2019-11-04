package org.carbon.runtime

// No parameters for now
open class Composite(val values: Map<String, CarbonObject> = mapOf(), var lexicalScope: Composite? = null) : CarbonObject() {
    override fun evaluate(scope: Composite): CarbonObject = this

    private val inputs: Map<String, CarbonObject> = mutableMapOf()

    override fun setScope(lexicalScope: Composite) {
        this.lexicalScope = lexicalScope
        values.values.forEach { it.setScope(this) }
    }

    fun lookupName(name: String): CarbonObject? = getMember(name) ?: lexicalScope?.lookupName(name)
    open fun getMember(name: String): CarbonObject? {
        if (values.containsKey(name)) {
            // No caching is done here. The entire computation graph is worked out at every step
            return values.getValue(name).evaluate(this)
        } else {
            // We don't have this member
            return null
        }
    }

    override fun toString(): String =
        "{ ${values.map { entry -> "${entry.key} = ${entry.value}" }.joinToString(", ")} }"
}