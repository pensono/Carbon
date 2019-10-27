package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.CarbonString
import org.carbon.runtime.Composite

class StringLiteral(val value : String) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject = CarbonString(value)
    override fun toString(): String = value
}