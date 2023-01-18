package uk.gibby.neo4k.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.returns.multiple.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.primitives.StringReturn

/**
 * Graph
 *
 * Represents a graph on a Redis graph and is the entry point for making graph queries.
 *
 * @property name The name of the graph to query
 * @constructor Connects to Redis and allows queries to a graph ([name])
 *
 * @param host The database host
 * @param port The database port
 * @param password The database password
 */
class Graph(
    internal val name: String,
    internal val username: String,
    internal val password: String,
    internal val host: String,
    internal val logRequests: Boolean
) {
    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(this@Graph.username, this@Graph.password)
                }
                realm = "Access to the '/' path"
            }
        }
    }
    /**
     * Query
     *
     * @param T The return type of the query
     * @param queryBuilder A scope for creating a query using clauses defined on [QueryScope]
     * @return Query result in the form of a list of [T]
     */
    fun <T>query(queryBuilder: QueryScope.() -> ReturnValue<T>): List<T>{
        val scope = QueryScope()
        val returnValue = scope.queryBuilder()
        val resultParser = if(returnValue is MultipleReturn<*>) ResultSetParser(returnValue.serializer as KSerializer<T?>)
            else ResultSetParser(SingleParser(returnValue.serializer as KSerializer<T?>).serializer)
        val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
        else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
        return when(returnValue){
            is EmptyReturn -> {
                runBlocking {
                client.post("http://${host}:7474/db/${name}/tx/commit"){
                    basicAuth(username, password)
                    contentType(ContentType.Application.Json)
                    setBody("{\"statements\" : [{\"statement\" : \"$queryString\"}]}")
                }}
                emptyList()
            }
            else -> {
                runBlocking {
                    val response = client.post("http://${host}:7474/db/${name}/tx/commit"){
                        basicAuth(username, password)
                        contentType(ContentType.Application.Json)
                        setBody("{\"statements\" : [{\"statement\" : \"$queryString\"}]}")
                    }
                    val resultSet = Json.decodeFromString(resultParser, response.bodyAsText())
                    resultSet.errors.forEach{
                        throw it.getError()
                    }
                    resultSet.notifications.forEach {
                        Neo4kLogger.info("{} {} {} {}", it.code, it.severity, it.title, it.description)
                    }
                    resultSet.data[0]
                } as List<T>
            }
        }
    }
    suspend fun sendQuery(query: String, params: String) = client.post("http://${host}:7474/db/${name}/tx/commit"){
        basicAuth(username, password)
        contentType(ContentType.Application.Json)
        setBody("{\"statements\" : [{\"statement\": \"$query\", \"parameters\": {$params}}]}")
    }.bodyAsText()
    /**
     * Delete
     *
     * Deletes the graph (graph by name: [name])
     */
    fun delete(){
        runBlocking {
            client.post("http://${host}:7474/db/${name}/tx/commit"){
                basicAuth(username, password)
                contentType(ContentType.Application.Json)
                setBody("{\"statements\" : [{\"statement\": \"MATCH (n) DETACH DELETE n\"}]}")
            }
        }
    }
    fun create(){
        runBlocking {
            val response = client.post("http://${host}:7474/db/neo4j/tx/commit"){
                basicAuth(username, password)
                contentType(ContentType.Application.Json)
                setBody("{\"statements\" : [{\"statement\": \"CREATE DATABASE $name IF NOT EXISTS\"}]}")
            }.bodyAsText()
            Json.decodeFromString(ResultSetParser(ReturnValue.createDummy(::StringReturn).serializer), response).apply {
                notifications.forEach { Neo4kLogger.info("{} {} {} {}", it.code, it.severity, it.title, it.description) }
                errors.forEach { throw it.getError() }
            }
        }
    }

}

