package org.carbon.syntax

import org.carbon.runtime.CarbonObject

class Identifier(val name: String) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject {
        return scope.lookupName(name) ?: error("$name not found in $scope")
    }

    override fun toString(): String = name
}