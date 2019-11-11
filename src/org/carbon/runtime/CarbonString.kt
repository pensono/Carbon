package org.carbon.runtime


class CarbonString(val value: String) : Composite() {
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "+" -> StringFunction(value, String::plus)
            "Length" -> CarbonInteger(value.length.toLong())
            else -> null
        }

    override fun toString(): String = "String($value)"
    override fun equals(other: Any?) = other is CarbonString && other.value == value
    override fun hashCode(): Int = value.hashCode()
}

private class StringFunction(val rhs: String, val function: (String, String) -> String) : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val lhs = arguments[0] as CarbonString

        return CarbonString(function(rhs, lhs.value))
    }
}