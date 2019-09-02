package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

open class Function(val parameters: List<String>, val body: CarbonSyntax, val lexicalScope: CarbonObject) : CarbonObject(), Callable {
    override fun lookupName(name: String): CarbonObject? = null
    override fun getMember(name: String): CarbonObject? = null

    override fun call(arguments: List<CarbonObject>) : CarbonObject {
        assert(parameters.size == arguments.size)

        val argumentMapping = parameters.zip(arguments).associateBy({ it.first }, { it.second })

        // Wraps around the outer scope
        val functionScope = object : CarbonObject() {
            override fun lookupName(name: String) : CarbonObject? = getMember(name) ?: lexicalScope.lookupName(name)
            override fun getMember(name: String) : CarbonObject? = argumentMapping[name]
        }

        return body.evaluate(functionScope)
    }
}