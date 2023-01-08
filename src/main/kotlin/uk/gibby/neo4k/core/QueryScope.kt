package uk.gibby.neo4k.core

import uk.gibby.neo4k.clauses.Clause

class QueryScope {
    private val clauses = mutableListOf<Clause>()
    private val afterClauses = mutableListOf<Clause>()
    fun addStatement(statement: Clause){ clauses.add(statement) }
    fun addStatementAfterReturn(statement: Clause){ afterClauses.add(statement) }


    fun getString() = clauses.joinToString(" "){it.getString()}
    fun getAfterString() = afterClauses.joinToString(" ") { it.getString() }
}