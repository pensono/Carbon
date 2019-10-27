package org.carbon.runtime


class CarbonBoolean(val value: Boolean) : Composite() {
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

private class BoolFunction(val rhs: Boolean, val function: (Boolean, Boolean) -> Boolean) : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val lhs = arguments[0] as CarbonBoolean

        return CarbonBoolean(function(rhs, lhs.value))
    }
}