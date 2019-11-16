package org.carbon.nativelib

import org.carbon.runtime.*
import org.carbon.runtime.Function
import org.carbon.syntax.CallSyntax
import org.carbon.syntax.ConditionSyntax
import org.carbon.syntax.DeclarationSyntax
import org.carbon.syntax.IdentifierSyntax

object TailCallAnnotation : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        assert(arguments.size == 1)

        val declaration = arguments[0] as DeclarationSyntax
        val function = declaration.body as Function

        return LoopFunction(function.parameters, armsOf(function.body, declaration.name), function.lexicalScope)
    }

    fun armsOf(body: CarbonObject, functionName: String) : List<LoopArm> =
        when(body) {
            is ConditionSyntax -> {
                listOf(makeArm(body.condition, body.trueBranch, functionName)) + armsOf(body.falseBranch, functionName)
            }
            else -> listOf(makeArm(CarbonBoolean(true), body, functionName))
        }

    fun makeArm(condition: CarbonObject, body: CarbonObject, functionName: String) : LoopArm =
        if (body is CallSyntax &&
            body.base is IdentifierSyntax &&
            body.base.name == functionName)
            LoopArm.RecursiveArm(condition, body.actualParameters)
        else
            LoopArm.BaseArm(condition, body)
}

sealed class LoopArm(val condition: CarbonObject) {
    class RecursiveArm(condition: CarbonObject, val argumentBodies: List<CarbonObject>) : LoopArm(condition)
    class BaseArm(condition: CarbonObject, val body: CarbonObject) : LoopArm(condition)
}

class LoopFunction(val formalParameters: List<String>, val arms: List<LoopArm>, val lexicalScope: Composite?) : Callable() {
    override fun apply(arguments: List<CarbonObject>): CarbonObject {
        var state = formalParameters.zip(arguments).associate { it }

        val scope =  object : Composite(mapOf(), lexicalScope) {
            override fun getMember(name: String) : CarbonObject? = state[name]
        }

        while (true) {
            val arm = arms.first { (it.condition.evaluate(scope) as CarbonBoolean).value }

            when (arm) {
                is LoopArm.RecursiveArm -> {
                    val arguments = arm.argumentBodies.map { it.evaluate(scope) }
                    state = formalParameters.zip(arguments).associate { it }
                }
                is LoopArm.BaseArm -> {
                    return arm.body.evaluate(scope)
                }
            }
        }
    }
}