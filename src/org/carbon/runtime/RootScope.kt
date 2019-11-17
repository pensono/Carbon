package org.carbon.runtime

import org.carbon.nativelib.*

// For now just dump everything in the root scope
private val rootMembers = mapOf<String, CarbonObject>(
    "HttpResource" to HttpResourceConstructor,
    "Memoize" to MemoizeAnnotation,
    "TailCall" to TailCallAnnotation,
    "CurrentTime" to CurrentTime
)

object RootScope : Composite(rootMembers) {
    override fun toString(): String = "RootScope"
}