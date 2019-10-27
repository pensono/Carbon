package org.carbon.syntax

import org.carbon.runtime.CarbonObject
import org.carbon.runtime.Composite

abstract class CarbonSyntax() {
    lateinit var linkedValue: CarbonObject

    fun link(scope: Composite) : CarbonObject {
        if (!this::linkedValue.isInitialized) {
            linkedValue = performLink(scope)
        }
        return linkedValue
    }

    abstract fun performLink(scope: Composite): CarbonObject
}