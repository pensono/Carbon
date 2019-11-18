package org.carbon.runtime

open class Function(val parameters: List<String>, val body: CarbonObject, var lexicalScope: Composite?) : Callable() {
    override fun apply(arguments: List<CarbonObject>) : CarbonObject {
        assert(parameters.size == arguments.size)

        val argumentMapping = parameters.zip(arguments).associate { it }

        // Wraps around the outer scope
        // TODO make this less awkward
        val functionScope = object : Composite(lexicalScope) {
            override fun getMember(name: String) : CarbonObject? = argumentMapping[name]
        }

        return body.evaluate(functionScope)
    }
}