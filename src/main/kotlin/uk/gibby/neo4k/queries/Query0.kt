package uk.gibby.neo4k.queries

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.ResultSetParser
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleReturn
import uk.gibby.neo4k.returns.empty.EmptyReturn

class Query0<T>(builder: QueryScope.() -> ReturnValue<T>) {
    private val scope = QueryScope()
    private val returnValue = scope.builder()
    private val resultParser = if(returnValue is MultipleReturn) ResultSetParser(returnValue) else ResultSetParser(SingleReturn(returnValue))
    private val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
        else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
    val query = if (returnValue is EmptyReturn) org.neo4j.driver.Query("${scope.getString()} ${scope.getAfterString()}")
        else org.neo4j.driver.Query("${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}")
    fun execute(graph: Graph): List<T>{
        val response = runBlocking { graph.sendQuery(queryString, "") }
        return Json.decodeFromString(resultParser, response).first()
    }
    companion object{
        fun <T>query(builder: QueryScope.() -> ReturnValue<T>): Graph.() -> List<T>{
            val query = Query0(builder)
            return { query.execute(this) }
        }
    }
}
