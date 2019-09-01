package org.carbon.syntax

import org.carbon.runtime.CarbonObject

class Accessor(val base: CarbonSyntax, val name: String) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject {
        val base = base.evaluate(scope)
        return base.lookupName(name) ?: error("Could not find $name in $scope")
    }
}