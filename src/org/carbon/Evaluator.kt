package org.carbon

import org.carbon.runtime.*

fun evaluate(program: String, property: String) : CarbonObject? {
    val parsed = parseFile(program) ?: error("Parse failed")
    val carbonObject = buildComposite(parsed.definitions)

    return carbonObject.values[property]?.evaluate()
}

private class ExpressionVisitor(val scope: CarbonSyntax) : CarbonBaseVisitor<CarbonSyntax>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonSyntax {
        val value = Integer.parseInt(ctx.text)
        return CarbonInteger(value)
    }

    override fun visitIdentifierExpr(ctx: CarbonParser.IdentifierExprContext): CarbonSyntax {
        return Identifier(ctx.text, scope)
    }
}

private fun buildComposite(definitions: Collection<CarbonParser.DefinitionContext>) : Composite {
    var inside : Composite? = null
    val scope = object : CarbonSyntax() {
        override fun evaluate(): CarbonObject = inside!!
    }

    val values = definitions.filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { ExpressionVisitor(scope).visit(it) })

    inside = Composite(values)
    return inside
}