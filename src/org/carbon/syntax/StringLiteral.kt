package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.CarbonString

class StringLiteral(val value : String) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject = CarbonString(value)
    override fun toString(): String = value
}