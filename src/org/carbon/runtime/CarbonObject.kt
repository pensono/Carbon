package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

abstract class CarbonObject() {
    abstract fun lookupName(name: String) : CarbonObject?
    abstract fun getMember(name: String) : CarbonObject?
}