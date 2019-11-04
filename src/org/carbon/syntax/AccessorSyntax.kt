package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class AccessorSyntax(val base: CarbonObject, val name: String) : CarbonSyntax() {
    override fun evaluate(scope: Composite): CarbonObject {
        val base = base.evaluate(scope)
        val baseComposite = base as? Composite ?: error("$base cannot be dereferenced")
        return base.getMember(name) ?: error("Could not find $name in $base")
    }

    override fun toString(): String = "$base.$name"
}