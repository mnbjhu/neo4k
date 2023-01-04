package uk.gibby.neo4k.queries

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.neo4j.driver.SessionConfig
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.NewGraph
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
    fun execute(graph: NewGraph): List<T>{
        return runBlocking {
            val response = graph.client.post("http://${graph.host}:7474/db/${graph.name}/tx/commit"){
                basicAuth(graph.username, graph.password)
                contentType(ContentType.Application.Json)
                setBody("{\"statements\" : [{\"statement\" : \"$queryString\"}]}")
            }
            Json.decodeFromString(resultParser as ResultSetParser<*, *>, response.bodyAsText()) as List<List<T>>
        }[0]
    }
    companion object{
        /*
        fun <T>query(builder: QueryScope.() -> ReturnValue<T>): Graph.() -> List<T>{

            val query = Query0(builder)
            return { query.execute(this) }
        }*/
    }
}
