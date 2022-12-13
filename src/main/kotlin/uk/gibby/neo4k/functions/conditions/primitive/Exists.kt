package uk.gibby.neo4k.functions.conditions.primitive

import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.Matchable
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.BooleanReturn

fun exists(queryBuilder: QueryScope.() -> Unit): BooleanReturn{
    val queryScope = QueryScope()
    val query = queryScope.apply(queryBuilder).getString()
    return ReturnValue.createReference(::BooleanReturn, "exists{$query}")
}
fun exists(matchable: Matchable<*>): BooleanReturn = exists{ match(matchable) }