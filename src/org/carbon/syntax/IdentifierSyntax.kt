package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class IdentifierSyntax(val name: String) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject {
        return scope.lookupName(name) ?: error("$name not found in $scope")
    }

    override fun toString(): String = name
}