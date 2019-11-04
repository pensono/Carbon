package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class IdentifierSyntax(val name: String) : CarbonSyntax() {
    override fun evaluate(scope: Composite): CarbonObject {
        return scope.lookupName(name) ?: error("Could not find $name in $scope")
    }

    override fun toString(): String = name
}