package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class CompositeSyntax(val values: Map<String, CarbonSyntax>) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject {
        return Composite(values, scope)
    }
}