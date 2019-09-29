package org.carbon.runtime


class CarbonString(val value: String) : CarbonObject() {
    override fun lookupName(name: String): CarbonObject? = getMember(name) // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "+" -> StringFunction(value, String::plus)
            else -> null
        }

    override fun toString(): String = "String($value)"
    override fun equals(other: Any?) = other is CarbonString && other.value == value
    override fun hashCode(): Int = value.hashCode()
}

private class StringFunction(val rhs: String, val function: (String, String) -> String) : CarbonObject(), Callable {
    override fun lookupName(name: String): CarbonObject? = null
    override fun getMember(name: String): CarbonObject? = null

    override fun call(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val lhs = arguments[0] as CarbonString

        return CarbonString(function(rhs, lhs.value))
    }
}