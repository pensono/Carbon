package org.carbon.runtime

import org.carbon.nativelib.HttpResourceConstructor

object RootScope : CarbonObject() {
    // For now just dump everything in the root scope
    private val members = mapOf(
        "HttpResource" to HttpResourceConstructor
    )

    override fun lookupName(name: String): CarbonObject? = getMember(name)
    override fun getMember(name: String): CarbonObject? = members[name]
    override fun toString(): String = "RootScope"
}