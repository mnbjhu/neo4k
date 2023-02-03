package uk.gibby.neo4k.queries

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.Neo4kLogger
import uk.gibby.neo4k.core.ResultSetParser
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.multiple.MultipleReturn

class Query<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val query: String, private val paramSerializers: List<KSerializer<*>>, val returnValue: R, private val hasReturn: Boolean){
    private val returnSerializer: ResultSetParser<out r?,out KSerializer<out r?>> = if(returnValue is MultipleReturn<*>) ResultSetParser(returnValue.serializer)
        else ResultSetParser(SingleParser(returnValue.serializer as KSerializer<r?>).serializer)
    fun execute(graph: Graph, args: List<Any?>): List<r?> {
        var count = 0
        val paramString = args.joinToString { "\"p$count\": " + Json.encodeToString(paramSerializers[count++] as KSerializer<Any?>, it) }
        val response = runBlocking { graph.sendQuery(query, paramString) }
        if(!hasReturn) return emptyList()
        val resultSet = Json.decodeFromString(returnSerializer, response)
        resultSet.notifications.forEach { Neo4kLogger.info("{} {} {} {}", it.code, it.severity, it.title, it.description) }
        resultSet.errors.forEach { Neo4kLogger.error("{} {}", it.code, it.message); throw it.getError() }
        return resultSet.data[0]
    }
}