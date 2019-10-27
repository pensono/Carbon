package org.carbon.syntax

import org.carbon.runtime.Composite

class CompositeSyntax(val values: Map<String, CarbonSyntax>) : CarbonSyntax() {
    override fun performLink(scope: Composite): Composite {
        return Composite(values, scope)
    }
}