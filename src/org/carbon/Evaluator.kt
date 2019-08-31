package org.carbon

import org.carbon.runtime.CarbonComposite
import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject

fun evaluate(program: String, property: String) : CarbonObject? {
    val parsed = parseFile(program) ?: return null
    val carbonObject = buildComposite(parsed.definitions)

    return carbonObject.values[property]
}

private object ExpressionVisitor : CarbonBaseVisitor<CarbonObject>() {
    override fun visitNumberLiteral(ctx: CarbonParser.NumberLiteralContext): CarbonObject {
        val value = Integer.parseInt(ctx.text)
        return CarbonInteger(value)
    }
}

private fun buildComposite(definitions: Collection<CarbonParser.DefinitionContext>) : CarbonComposite {
    val values = definitions.filterIsInstance<CarbonParser.DeclarationContext>()
        .associateBy({ it.name.text }, { ExpressionVisitor.visit(it) })

    return CarbonComposite(values)
}