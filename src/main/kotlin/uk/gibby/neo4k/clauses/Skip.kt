package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.primitives.LongReturn

class Skip(private val count: LongReturn): Claus(){
    constructor(long: Long): this(::LongReturn of long)
    override fun getString(): String {
        return "SKIP ${count.getString()}"
    }

    companion object{
        fun QueryScope.skip(count: LongReturn) = Skip(count).also { addStatementAfterReturn(it) }
        fun QueryScope.skip(count: Long) = Skip(count).also { addStatementAfterReturn(it) }
    }

}