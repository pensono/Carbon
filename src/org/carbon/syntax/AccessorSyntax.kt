package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class AccessorSyntax(val base: CarbonSyntax, val name: String) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject {
        val base = base.performLink(scope)
        val baseComposite = base as? Composite ?: error("$base cannot be dereferenced")
        return base.getMember(name) ?: error("Could not find $name in $scope")
    }

    override fun toString(): String = "$base.$name"
}