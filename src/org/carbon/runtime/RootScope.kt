package org.carbon.runtime

import org.carbon.nativelib.*

object RootScope : Composite() {
    // For now just dump everything in the root scope
    private val members = mapOf<String, CarbonObject>(
        "HttpResource" to HttpResourceConstructor,
        "Memoize" to MemoizeAnnotation,
        "TailCall" to TailCallAnnotation
    )

    override fun getMember(name: String): CarbonObject? = members[name]
    override fun toString(): String = "RootScope"
}