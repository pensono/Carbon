package org.carbon.syntax

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonObject

class IntegerLiteral(val value : Int) : CarbonSyntax() {
    override fun evaluate(scope: CarbonObject): CarbonObject = CarbonInteger(value)
    override fun toString(): String = value.toString()
}