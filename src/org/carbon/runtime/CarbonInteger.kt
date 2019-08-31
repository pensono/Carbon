package org.carbon.runtime

class CarbonInteger(val value: Int) : CarbonObject() {
    override fun toString(): String = "Integer($value)"
    override fun equals(other: Any?) = other is CarbonInteger && other.value == value
    override fun hashCode(): Int = value
}