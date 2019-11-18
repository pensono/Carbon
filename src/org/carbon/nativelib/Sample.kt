package org.carbon.nativelib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.carbon.runtime.*
import org.carbon.syntax.DeclarationSyntax


object SampleConstructor : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val delay = arguments[0] as CarbonInteger

        return SampleAnnotation(delay.value)
    }
}

class SampleAnnotation(val delay: Long) : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val declaration = arguments[0] as DeclarationSyntax
        val body = declaration.body
        val scope = declaration.scope

        return SampledValue(body, scope ,delay)
    }
}


class SampledValue(val body: CarbonObject, val scope: Composite, delayMs: Long) : CarbonObject() {
    var sampledValue : CarbonObject = body.evaluate(scope)

    init {
        GlobalScope.launch {
            while (true) {
                delay(delayMs)
                sampledValue = body.evaluate(scope)
            }
        }
    }

    override fun evaluate(scope: Composite): CarbonObject {
        return sampledValue
    }
}
