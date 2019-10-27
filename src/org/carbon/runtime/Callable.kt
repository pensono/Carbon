package org.carbon.runtime

abstract class Callable : CarbonObject() {
    override fun evaluate(): CarbonObject = this
    abstract fun apply(arguments: List<CarbonObject>) : CarbonObject
}
