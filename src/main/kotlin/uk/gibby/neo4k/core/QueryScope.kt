package uk.gibby.neo4k.core

import uk.gibby.neo4k.clauses.*
import uk.gibby.neo4k.queries.QueryBuilder
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.primitives.LongReturn

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
    infix fun <U: ReturnValue<*>>U.limit(count: LongReturn): U = this.also { addStatementAfterReturn(Limit(count)) }
    infix fun <U: ReturnValue<*>>U.limit(count: Long): U = this.also { addStatementAfterReturn(Limit(count)) }
    infix fun <U: ReturnValue<*>> U.orderBy(result: ReturnValue<*>): U {
        addStatementAfterReturn(OrderBy(result))
        return this
    }
    infix fun <U: ReturnValue<*>>U.orderByDesc(result: ReturnValue<*>): U{
        addStatementAfterReturn(OrderBy(result, true))
        return this
    }
    infix fun <U: ReturnValue<*>>U.skip(count: LongReturn) = this.also { addStatementAfterReturn(Skip(count)) }
    infix fun <U: ReturnValue<*>>U.skip(count: Long) = this.also { addStatementAfterReturn(Skip(count)) }
}