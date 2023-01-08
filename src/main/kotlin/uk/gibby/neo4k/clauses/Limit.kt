package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.primitives.LongReturn

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