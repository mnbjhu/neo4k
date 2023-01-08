package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue

class OrderBy<T>(private val comparable: ReturnValue<T>, private val descending: Boolean = false): Clause() {

    companion object {
        fun <T: Any> QueryScope.orderBy(result: ReturnValue<T>) {
            addStatementAfterReturn(OrderBy(result))
        }
        fun <T: Any>QueryScope.orderByDesc(result: ReturnValue<T>) {
            addStatementAfterReturn(OrderBy(result, true))
        }
    }

    override fun getString(): String {
        return "ORDER BY ${comparable.getString()}${if(descending)" DESC" else ""}"
    }

}