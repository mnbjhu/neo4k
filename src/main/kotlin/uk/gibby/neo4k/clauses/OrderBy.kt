package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance

class OrderBy<T>(private val comparable: ReturnValue<T>, private val descending: Boolean = false): Clause() {

    companion object {
        fun <T: Any> QueryScope.orderBy(result: ReturnValue<T>): EmptyReturn {
            addStatementAfterReturn(OrderBy(result))
            return EmptyReturnInstance
        }
        fun <T: Any>QueryScope.orderByDesc(result: ReturnValue<T>): EmptyReturn  {
            addStatementAfterReturn(OrderBy(result, true))
            return EmptyReturnInstance
        }
    }

    override fun getString(): String {
        return "ORDER BY ${comparable.getString()}${if(descending)" DESC" else ""}"
    }

}