package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.primitives.LongReturn

/**
 * Create
 *
 * Represents the 'LIMIT' clause from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/limit/)
 *
 * @constructor creates a 'LIMIT' clause
 */
class Limit(private val count: LongReturn): Clause(){
    constructor(long: Long): this(::LongReturn of long)
    override fun getString(): String {
        return "LIMIT ${count.getString()}"
    }
    companion object{
        fun QueryScope.limit(count: LongReturn) = Limit(count).also { addStatementAfterReturn(it) }
        fun QueryScope.limit(count: Long) = Limit(count).also { addStatementAfterReturn(it) }
    }
}