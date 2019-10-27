package org.carbon

import org.carbon.runtime.*
import org.carbon.syntax.*

fun evaluate(program: String) : Composite {
    val parsed = parseFile(program) ?: error("Parse failed")

    return buildComposite(parsed.definitions, RootScope)
}

private object ExpressionVisitor : CarbonParserBaseVisitor<CarbonSyntax>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonSyntax {
        val value = Integer.parseInt(ctx.text)
        return IntegerLiteral(value)
    }

    override fun visitStringLiteral(ctx: CarbonParser.StringLiteralContext): CarbonSyntax {
        // If content is missing, then it's the empty string literal
        return StringLiteral(ctx.content?.text ?: "")
    }

    override fun visitIdentifierExpr(ctx: CarbonParser.IdentifierExprContext): CarbonSyntax {
        return IdentifierSyntax(ctx.identifier().text)
    }

    override fun visitAccessorExpr(ctx: CarbonParser.AccessorExprContext): CarbonSyntax {
        val base = visit(ctx.base)
        return AccessorSyntax(base, ctx.name.text)
    }

    override fun visitCallExpr(ctx: CarbonParser.CallExprContext): CarbonSyntax {
        val formalParameters = ctx.argumentList().parameters.map { visit(it) }
        val base = visit(ctx.base)

        return CallSyntax(formalParameters, base)
    }

    override fun visitOperatorExpr(ctx: CarbonParser.OperatorExprContext): CarbonSyntax {
        val lhs = visit(ctx.lhs)
        val rhs = visit(ctx.rhs)
        val operator = ctx.operator.text

        return CallSyntax(listOf(rhs), AccessorSyntax(lhs, operator))
    }

    override fun visitCompositeExpr(ctx: CarbonParser.CompositeExprContext): CarbonSyntax {
        val values = ctx.expressionBody().definitions
            .filterIsInstance<CarbonParser.DeclarationContext>()
            .associateBy({ it.name.text }, { visit(it) })
        val parameters = ctx.expressionBody().definitions
            .filterIsInstance<CarbonParser.ParameterContext>()
            .map { it.param().name.text }

        return if (parameters.isEmpty())
            CompositeSyntax(values)
        else
            FunctionSyntax(parameters, CompositeSyntax(values))
    }
}

private fun visitCompositeBody(definitions: Collection<CarbonParser.DefinitionContext>) : CarbonSyntax {
    val values = definitions
        .filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { ExpressionVisitor.visit(it) })
    val parameters = definitions
        .filterIsInstance<CarbonParser.ParameterContext>()
        .map { it.param().name.text }

    return if (parameters.isEmpty())
        CompositeSyntax(values)
    else
        FunctionSyntax(parameters, CompositeSyntax(values))
}

private fun buildComposite(definitions: Collection<CarbonParser.DefinitionContext>, lexicalScope: Composite) : Composite {
    val values = definitions.filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { declaration ->
            val body = ExpressionVisitor.visit(declaration)
            if (declaration.parameterList() == null)
                body
            else {
                val formalParameters = declaration.parameterList().parameters.map { it.name.text }
                FunctionSyntax(formalParameters, body)
            }
        })

    return Composite(values, lexicalScope)
}