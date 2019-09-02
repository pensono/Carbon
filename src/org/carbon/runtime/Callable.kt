package org.carbon.runtime

// Not sure if this interface is the right way to abstract this
interface Callable {
    fun call(arguments: List<CarbonObject>) : CarbonObject
}