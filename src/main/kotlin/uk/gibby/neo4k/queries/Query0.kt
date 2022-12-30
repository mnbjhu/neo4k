package uk.gibby.neo4k.queries

import org.neo4j.driver.SessionConfig
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn

class Query0<T>(builder: QueryScope.() -> ReturnValue<T>) {
    private val scope = QueryScope()
    private val returnValue = scope.builder()
    val query = if (returnValue is EmptyReturn) org.neo4j.driver.Query("${scope.getString()} ${scope.getAfterString()}")
        else org.neo4j.driver.Query("${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}")
    fun execute(graph: Graph): List<T> = if(returnValue is EmptyReturn){
        graph.driver.session(SessionConfig.forDatabase(graph.name)).executeWrite{
            it.run(query)
        }
        emptyList()
    } else {
        graph.driver.session(SessionConfig.forDatabase(graph.name)).executeWrite{
            if(returnValue is MultipleReturn) it.run(query).list { returnValue.parse(it.values()) }
            else { it.run(query).list { returnValue.parse(it.values().first()) } }
        }
    }
    companion object{
        fun <T>query(builder: QueryScope.() -> ReturnValue<T>): Graph.() -> List<T>{
            val query = Query0(builder)
            return { query.execute(this) }
        }
    }
}
