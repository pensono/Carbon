package org.carbon.runtime

abstract class CarbonObject {
    abstract fun evaluate(scope: Composite): CarbonObject
    open fun setScope(lexicalScope: Composite) { }
}