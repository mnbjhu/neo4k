package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.primitives.BooleanReturn

class Where(private val predicate: BooleanReturn): Clause() {
    override fun getString(): String {
        return "WHERE ${predicate.getString()}"
    }
    companion object{
        infix fun QueryScope.where(predicate: BooleanReturn) = EmptyReturnInstance.also { addStatement(Where(predicate)) }
    }
}