package org.carbon.nativelib

import org.carbon.runtime.Callable
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite
import org.carbon.syntax.DeclarationSyntax

object MemoizeAnnotation : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val declaration = arguments[0] as DeclarationSyntax
        val syntax = declaration.body as Callable

        return MemoizedFunction(syntax)
    }
}

class MemoizedFunction(val body: Callable) : Callable() {
    private val lookup = mutableMapOf<List<CarbonObject>, CarbonObject>()

    override fun setScope(lexicalScope: Composite) {
        body.setScope(lexicalScope)
    }

    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        // Can't use computeIfAbsent here because of concurrent modification
        val cached = lookup[arguments]
        if (cached != null) {
            return cached
        }

        val result = body.apply(arguments)
        lookup[arguments] = result
        return result
    }
}