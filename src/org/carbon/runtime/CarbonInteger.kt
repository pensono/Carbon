package org.carbon.runtime

import org.carbon.syntax.CarbonSyntax

class CarbonInteger(val value: Int) : CarbonObject() {
    override fun lookupName(name: String): CarbonObject? = null // Should this actually do some work with the scope?
    override fun getMember(name: String): CarbonObject? = null

    override fun toString(): String = "Integer($value)"
    override fun equals(other: Any?) = other is CarbonInteger && other.value == value
    override fun hashCode(): Int = value
}