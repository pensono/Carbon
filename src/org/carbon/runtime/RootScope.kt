package org.carbon.runtime

object RootScope : CarbonObject() {
    override fun lookupName(name: String): CarbonObject? = null
    override fun getMember(name: String): CarbonObject? = null
    override fun toString(): String = "RootScope"
}