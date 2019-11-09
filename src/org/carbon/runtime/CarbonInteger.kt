package org.carbon.runtime


class CarbonInteger(var value: Long) : Composite() {
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "+" -> IntMagma(Long::plus)
            "-" -> IntMagma(Long::minus)
            "*" -> IntMagma(Long::times)
            "<" -> IntRelation { a, b -> a < b }
            ">" -> IntRelation { a, b -> a > b }
            else -> null
        }

    override fun toString(): String = "Integer($value)"
    override fun equals(other: Any?) = other is CarbonInteger && other.value == value
    override fun hashCode(): Int = value.hashCode()

    private inner class IntMagma(val function: (Long, Long) -> Long) : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonInteger

            return CarbonInteger(function(value, lhs.value))
        }
    }

    private inner class IntRelation(val function: (Long, Long) -> Boolean) : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonInteger

            return CarbonBoolean(function(value, lhs.value))
        }
    }
}
