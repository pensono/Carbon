package org.carbon.runtime

// No parameters for now
open class Composite(var lexicalScope: Composite? = null) : CarbonObject() {
    private val members: MutableMap<String, CarbonObject> = mutableMapOf()
    private val inputs: Map<String, CarbonObject> = mutableMapOf()

    override fun evaluate(scope: Composite): CarbonObject = this

    fun addMember(name: String, member: CarbonObject) {
        members[name] = member
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