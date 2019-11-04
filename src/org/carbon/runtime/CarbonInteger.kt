package org.carbon.runtime


class CarbonInteger(var value: Int) : Composite() {
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "+" -> IntMagma(Int::plus)
            "-" -> IntMagma(Int::minus)
            "*" -> IntMagma(Int::times)
            "<" -> IntRelation { a, b -> a < b }
            ">" -> IntRelation { a, b -> a > b }
            else -> null
        }

    override fun toString(): String = "Integer($value)"
    override fun equals(other: Any?) = other is CarbonInteger && other.value == value
    override fun hashCode(): Int = value

    private inner class IntMagma(val function: (Int, Int) -> Int) : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonInteger

            return CarbonInteger(function(value, lhs.value))
        }
    }

    private inner class IntRelation(val function: (Int, Int) -> Boolean) : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonInteger

            return CarbonBoolean(function(value, lhs.value))
        }
    }
}
