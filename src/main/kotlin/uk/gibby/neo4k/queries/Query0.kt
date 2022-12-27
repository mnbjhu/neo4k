package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
/*
class Query0<T, U: ReturnValue<T>>(val query: QueryScope.() -> U) {
    private val result: U
    private val queryString: String
    init {
        val scope = QueryScope()
        result = scope.query()
        queryString = if(result is EmptyReturn) scope.getString()
            else "${scope.getString()} RETURN $result"
    }
    fun execute(graph: Graph): List<T>{
        graph.client
    }
}

 */