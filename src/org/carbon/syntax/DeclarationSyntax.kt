package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class DeclarationSyntax(val name: String, val body: CarbonObject) : CarbonSyntax() {
    override fun evaluate(scope: Composite): CarbonObject {
        return this // Not sure what to do here
    }
}