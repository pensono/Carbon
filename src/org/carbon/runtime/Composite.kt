package org.carbon.runtime

// No parameters for now
open class Composite(val members: Map<String, CarbonObject> = mapOf(), var lexicalScope: Composite? = null) : CarbonObject() {
    override fun evaluate(scope: Composite): CarbonObject = this

    private val inputs: Map<String, CarbonObject> = mutableMapOf()

    override fun setScope(lexicalScope: Composite) {
        this.lexicalScope = lexicalScope
        members.values.forEach { it.setScope(this) }
    }

    fun lookupName(name: String): CarbonObject? = getMember(name) ?: lexicalScope?.lookupName(name)
    open fun getMember(name: String): CarbonObject? {
        if (members.containsKey(name)) {
            // No caching is done here. The entire computation graph is worked out at every step
            return members.getValue(name).evaluate(this)
        } else {
            // We don't have this member
            return null
        }
    }

    override fun toString(): String =
        "{ ${members.map { entry -> "${entry.key} = ${entry.value}" }.joinToString(", ")} }"
}