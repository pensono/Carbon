package org.carbon.runtime


class CarbonInteger(val value: Int) : CarbonObject() {
    override fun lookupName(name: String): CarbonObject? = getMember(name) // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "+" -> IntFunction(value, Int::plus)
            "-" -> IntFunction(value, Int::minus)
            "*" -> IntFunction(value, Int::times)
            else -> null
        }

    override fun toString(): String = "Integer($value)"
    override fun equals(other: Any?) = other is CarbonInteger && other.value == value
    override fun hashCode(): Int = value
}

private class IntFunction(val rhs: Int, val function: (Int, Int) -> Int) : CarbonObject(), Callable {
    override fun lookupName(name: String): CarbonObject? = null
    override fun getMember(name: String): CarbonObject? = null

    override fun call(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val lhs = arguments[0] as CarbonInteger

        return CarbonInteger(function(rhs, lhs.value))
    }
}