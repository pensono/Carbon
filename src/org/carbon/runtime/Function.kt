package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

open class Function(val parameters: List<String>, val body: CarbonSyntax, val lexicalScope: Composite) : Callable() {
    override fun apply(arguments: List<CarbonObject>) : CarbonObject {
        assert(parameters.size == arguments.size)

        val argumentMapping = parameters.zip(arguments).associateBy({ it.first }, { it.second })

        // Wraps around the outer scope
        val functionScope = object : Composite(mapOf(), lexicalScope) {
            override fun getMember(name: String) : CarbonObject? = argumentMapping[name]
        }

        return body.performLink(functionScope)
    }
}