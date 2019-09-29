package org.carbon

import org.carbon.runtime.CarbonInteger
import org.carbon.runtime.CarbonString

fun wrap(int: Int) = CarbonInteger(int)
fun wrap(string: String) = CarbonString(string)