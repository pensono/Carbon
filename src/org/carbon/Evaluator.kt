package org.carbon

import org.carbon.runtime.*
import org.carbon.syntax.*

fun evaluate(program: String) : Composite {
    val parsed = parseFile(program) ?: error("Parse failed")

    return visitCompositeBody(parsed.definitions, RootScope) as Composite
}

private class ExpressionVisitor(val lexicalScope: Composite) : CarbonParserBaseVisitor<CarbonObject>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonObject {
        val value = Integer.parseInt(ctx.text)
        return CarbonInteger(value.toLong())
    }

    override fun visitStringLiteral(ctx: CarbonParser.StringLiteralContext): CarbonObject {
        // If content is missing, then it's the empty string literal
        return CarbonString(ctx.content?.text ?: "")
    }

    override fun visitListExpr(ctx: CarbonParser.ListExprContext): CarbonObject {
        val elements = ctx.elements.map { visit(it) }
        return CarbonList(elements)
    }

    override fun visitIdentifierExpr(ctx: CarbonParser.IdentifierExprContext): CarbonObject {
        return IdentifierSyntax(ctx.identifier().text)
    }

    override fun visitAccessorExpr(ctx: CarbonParser.AccessorExprContext): CarbonObject {
        val base = visit(ctx.base)
        return AccessorSyntax(base, ctx.name.text)
    }

    override fun visitDeclaration(ctx: CarbonParser.DeclarationContext): CarbonObject {
        var body = visit(ctx.body)

        for ((armTest, armBody) in ctx.conditions.zip(ctx.condition_vals)) {
            val testSyntax = visit(armTest)
            val bodySyntax = visit(armBody)
            body = ConditionSyntax(testSyntax, bodySyntax, body)
        }

        body = if (ctx.parameterList() == null)
            body
        else {
            val formalParameters = ctx.parameterList().parameters.map { it.name.text }
            Function(formalParameters, body, lexicalScope)
        }

        for (annotationSyntax in ctx.annotations) {
            // Should the declaration itself be returned by this visitor?
            val declarationSyntax = DeclarationSyntax(ctx.name.text, body)

            // What scope to pass here?
            val annotation = visit(annotationSyntax).evaluate(RootScope) as Callable
            body = annotation.apply(listOf(declarationSyntax))
        }

        return body
    }

    override fun visitCallExpr(ctx: CarbonParser.CallExprContext): CarbonObject {
        val formalParameters = ctx.argumentList().parameters.map { visit(it) }
        val base = visit(ctx.base)

        return CallSyntax(formalParameters, base)
    }

    override fun visitOperatorExpr(ctx: CarbonParser.OperatorExprContext): CarbonObject {
        val lhs = visit(ctx.lhs)
        val rhs = visit(ctx.rhs)
        val operator = ctx.operator.text

        return CallSyntax(listOf(rhs), AccessorSyntax(lhs, operator))
    }

    override fun visitCompositeExpr(ctx: CarbonParser.CompositeExprContext): CarbonObject {
        return visitCompositeBody(ctx.expressionBody().definitions, lexicalScope)
    }
}

private fun visitCompositeBody(definitions: Collection<CarbonParser.DefinitionContext>, lexicalScope: Composite) : CarbonObject {
    val values = definitions
        .filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { ExpressionVisitor(lexicalScope).visit(it) })
    val parameters = definitions
        .filterIsInstance<CarbonParser.ParameterContext>()
        .map { it.param().name.text }

    return if (parameters.isEmpty()) {
        val composite = Composite(values, lexicalScope)
        values.values.forEach{ it.setScope(composite) }
        composite
    } else {
        val body = object : CarbonObject() {
            override fun evaluate(scope: Composite): CarbonObject = Composite(values, scope)
        }

        Function(parameters, body, lexicalScope)
    }
}