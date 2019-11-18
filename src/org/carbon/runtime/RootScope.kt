package org.carbon.runtime

import org.carbon.nativelib.*

// For now just dump everything in the root scope
private val rootMembers = mapOf<String, CarbonObject>(
    "HttpResource" to HttpResourceConstructor,
    "Memoize" to MemoizeAnnotation,
    "TailCall" to TailCallAnnotation,
    "CurrentTime" to CurrentTime,
    "Sample" to SampleConstructor,
    "File" to FileConstructor
)

object RootScope : Composite() {
    override fun toString(): String = "RootScope"
    override fun getMember(name: String): CarbonObject? = rootMembers[name]
}