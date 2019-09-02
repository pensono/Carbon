package org.carbon.runtime

abstract class CarbonObject() {
    abstract fun lookupName(name: String) : CarbonObject?
    abstract fun getMember(name: String) : CarbonObject?
}