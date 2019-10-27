package org.carbon.runtime

import org.carbon.nativelib.HttpResourceConstructor

object RootScope : Composite(mapOf(), null) {
    // For now just dump everything in the root scope
    private val members = mapOf<String, CarbonObject>(
        "HttpResource" to HttpResourceConstructor
    )

    override fun getMember(name: String): CarbonObject? = members[name]
    override fun toString(): String = "RootScope"
}