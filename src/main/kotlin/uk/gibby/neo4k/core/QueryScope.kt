package uk.gibby.neo4k.core

import uk.gibby.neo4k.clauses.Call
import uk.gibby.neo4k.clauses.Clause
import uk.gibby.neo4k.queries.QueryBuilder
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn

class QueryScope {
    private val clauses = mutableListOf<Clause>()
    private val afterClauses = mutableListOf<Clause>()
    fun addStatement(statement: Clause){ clauses.add(statement) }
    fun addStatementAfterReturn(statement: Clause){ afterClauses.add(statement) }


    fun getString() = clauses.joinToString(" "){it.getString()}
    fun getAfterString() = afterClauses.joinToString(" ") { it.getString() }

    fun <T, U: ReturnValue<T>>QueryBuilder<Unit, EmptyReturn, T, U>.call(): U {
        val query = _build()
        addStatement(Call(query))
        return query.returnValue
    }
}