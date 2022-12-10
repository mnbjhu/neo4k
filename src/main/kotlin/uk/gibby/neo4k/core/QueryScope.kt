package uk.gibby.neo4k.core

import uk.gibby.neo4k.clauses.Claus

class QueryScope {
    private val clauses = mutableListOf<Claus>()
    fun addStatement(statement: Claus){ clauses.add(statement) }

    fun getString() = clauses.joinToString(" "){it.getString()}

}