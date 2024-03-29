package org.carbon.intrepreter

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonList
import org.carbon.runtime.CarbonObject
import org.carbon.runtime.CarbonString

fun wrap(int: Long) = CarbonInteger(int)
fun wrap(string: String) = CarbonString(string)
fun wrap(vararg values: Long) = CarbonList(values.map { CarbonInteger(it) })

fun unwrap(value: CarbonInteger) = value.value