package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.primitives.LongReturn

class Skip(private val count: LongReturn): Clause(){
    constructor(long: Long): this(::LongReturn of long)
    override fun getString(): String {
        return "SKIP ${count.getString()}"
    }

    companion object{
        fun QueryScope.skip(count: LongReturn) : EmptyReturn= EmptyReturnInstance.also { addStatementAfterReturn(Skip(count)) }
        fun QueryScope.skip(count: Long): EmptyReturn = EmptyReturnInstance.also { addStatementAfterReturn(Skip(count)) }
    }

}