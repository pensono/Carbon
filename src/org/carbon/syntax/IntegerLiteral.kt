package org.carbon.syntax

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

class IntegerLiteral(val value : Int) : CarbonSyntax() {
    override fun performLink(scope: Composite): CarbonObject = CarbonInteger(value)
    override fun toString(): String = value.toString()
}