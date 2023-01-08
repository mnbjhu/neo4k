package uk.gibby.neo4k.queries

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.Neo4kLogger
import uk.gibby.neo4k.core.ResultSetParser
import uk.gibby.neo4k.returns.ReturnValue

class Query<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val query: String, private val paramSerializers: List<KSerializer<*>>, private val returnSerializer: ResultSetParser<out r?, out KSerializer<out r?>>, private val hasReturn: Boolean){
    fun execute(graph: Graph, args: List<Any?>): List<r?> {
        var count = 0
        val paramString = args.joinToString { "\"p$count\": " + Json.encodeToString(paramSerializers[count++] as KSerializer<Any?>, it) }
        val response = runBlocking { graph.sendQuery(query, paramString) }
        if(!hasReturn) return emptyList()
        val resultSet = Json.decodeFromString(returnSerializer, response)
        resultSet.notifications.forEach {
            Neo4kLogger.info("{} {} {} {}", it.code, it.severity, it.title, it.description)
        }
        resultSet.errors.forEach{
            throw it.getError()
        }
        return resultSet.data[0]
    }
}