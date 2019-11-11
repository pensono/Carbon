package org.carbon.runtime

class CarbonList(val elements : List<CarbonObject>) : Composite() {
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            ":" -> MapFunction
            "+" -> ListMagma { a, b -> a + b }
            "Length" -> CarbonInteger(elements.size.toLong())
            else -> null
        }

    override fun toString(): String = "[" + elements.map { v -> v.toString() } + "]"
    override fun equals(other: Any?) = other is CarbonList && other.elements == elements
    override fun hashCode(): Int = elements.hashCode()

    private inner class ListMagma(val function: (List<CarbonObject>, List<CarbonObject>) -> List<CarbonObject>) : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonList

            return CarbonList(function(elements, lhs.elements))
        }
    }

    private object MapFunction : Callable() {
        override fun apply(arguments: List<CarbonObject>): CarbonObject {
            assert(arguments.size == 1)

            val lhs = arguments[0] as CarbonList

            return CarbonInteger(-1)
        }
    }
}
