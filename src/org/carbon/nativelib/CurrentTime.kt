package org.carbon.nativelib

import org.carbon.runtime.*
import org.carbon.runtime.Function
import org.carbon.syntax.CallSyntax
import org.carbon.syntax.ConditionSyntax
import org.carbon.syntax.DeclarationSyntax
import org.carbon.syntax.IdentifierSyntax

object CurrentTime : Composite() {
    override fun getMember(name: String): CarbonObject? =
        when (name) {
            "UnixMillis" -> CarbonInteger(System.currentTimeMillis())
            else -> null
        }
}
