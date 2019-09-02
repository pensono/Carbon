package org.carbon

import org.carbon.runtime.*
import org.carbon.syntax.*

fun evaluate(program: String, property: String) : CarbonObject? {
    val parsed = parseFile(program) ?: error("Parse failed")
    val carbonObject = buildComposite(parsed.definitions, RootScope)

    if (!carbonObject.values.containsKey(property)) {
        error("Could not find $property in $carbonObject")
    }

    return carbonObject.values.getValue(property).evaluate(carbonObject)
}

private class ExpressionVisitor : CarbonBaseVisitor<CarbonSyntax>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonSyntax {
        val value = Integer.parseInt(ctx.text)
        return IntegerLiteral(value)
    }

    override fun visitIdentifierExpr(ctx: CarbonParser.IdentifierExprContext): CarbonSyntax {
        return Identifier(ctx.identifier().text)
    }

    override fun visitAccessorExpr(ctx: CarbonParser.AccessorExprContext): CarbonSyntax {
        val base = visit(ctx.base)
        return Accessor(base, ctx.name.text)
    }

    override fun visitCallExpr(ctx: CarbonParser.CallExprContext): CarbonSyntax {
        val formalParameters = ctx.argumentList().parameters.map { ExpressionVisitor().visit(it) }
        val base = ExpressionVisitor().visit(ctx.base)

        return CallSyntax(formalParameters, base)
    }

    override fun visitCompositeExpr(ctx: CarbonParser.CompositeExprContext): CarbonSyntax {
        val values = ctx.expressionBody().definitions
            .filterIsInstance<CarbonParser.DeclarationContext>()
            .associateBy({ it.name.text }, { ExpressionVisitor().visit(it) })
        val parameters = ctx.expressionBody().definitions
            .filterIsInstance<CarbonParser.ParameterContext>()
            .map { it.param().name.text }

        return if (parameters.isEmpty())
            CompositeSyntax(values)
        else
            FunctionSyntax(parameters, CompositeSyntax(values))
    }
}

private fun buildComposite(definitions: Collection<CarbonParser.DefinitionContext>, lexicalScope: CarbonObject) : Composite {
    val values = definitions.filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { declaration ->
            val body = ExpressionVisitor().visit(declaration)
            if (declaration.parameterList() == null)
                body
            else {
                val formalParameters = declaration.parameterList().parameters.map { it.name.text }
                FunctionSyntax(formalParameters, body)
            }
        })

    return Composite(values, lexicalScope)
}