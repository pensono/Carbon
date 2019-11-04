package org.carbon.runtime

open class Function(val parameters: List<String>, val body: CarbonObject, var lexicalScope: Composite?) : Callable() {
    override fun setScope(lexicalScope: Composite) {
        this.lexicalScope = lexicalScope
    }

    override fun apply(arguments: List<CarbonObject>) : CarbonObject {
        assert(parameters.size == arguments.size)

        val argumentMapping = parameters.zip(arguments).associateBy({ it.first }, { it.second })

        // Wraps around the outer scope
        val functionScope = object : Composite(mapOf(), lexicalScope) {
            override fun getMember(name: String) : CarbonObject? = argumentMapping[name]
        }

        return body.evaluate(functionScope)
    }
}