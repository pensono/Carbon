package org.carbon.runtime

class Identifier(val name: String, private val scope: CarbonSyntax) : CarbonSyntax() {
    override fun evaluate(): CarbonObject {
        val evaluatedScope = scope.evaluate() as Composite? ?: error("$scope is not a composite")
        val referent = evaluatedScope.values[name] ?: error("$name not found in $scope")
        return referent.evaluate()
    }
}