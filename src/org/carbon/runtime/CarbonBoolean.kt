package org.carbon.runtime


class CarbonBoolean(val value: Boolean) : CarbonObject() {
    override fun lookupName(name: String): CarbonObject? = getMember(name) // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "&" -> BoolFunction(value, Boolean::and)
            "|" -> BoolFunction(value, Boolean::or)
            else -> null
        }

    override fun toString(): String = "Boolean($value)"
    override fun equals(other: Any?) = other is CarbonBoolean && other.value == value
    override fun hashCode(): Int = value.hashCode()
}

private class BoolFunction(val rhs: Boolean, val function: (Boolean, Boolean) -> Boolean) : CarbonObject(), Callable {
    override fun lookupName(name: String): CarbonObject? = null
    override fun getMember(name: String): CarbonObject? = null

    override fun call(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val lhs = arguments[0] as CarbonBoolean

        return CarbonBoolean(function(rhs, lhs.value))
    }
}