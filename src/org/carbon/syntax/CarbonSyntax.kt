package org.carbon.syntax

import org.carbon.runtime.CarbonObject

abstract class CarbonSyntax() {
    abstract fun evaluate(scope: CarbonObject): CarbonObject
}