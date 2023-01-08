package uk.gibby.neo4k.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.empty.EmptyReturn

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
    val username: String,
    val password: String,
    val host: String
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
                }.also { println(it.bodyAsText()) }}
                emptyList()
            }
            else -> {
                runBlocking {
                    val response = client.post("http://${host}:7474/db/${name}/tx/commit"){
                        basicAuth(username, password)
                        contentType(ContentType.Application.Json)
                        setBody("{\"statements\" : [{\"statement\" : \"$queryString\"}]}".also { println(it) })
                    }
                    Json.decodeFromString(resultParser, response.bodyAsText().also { println(it) })[0]
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

           /*
    fun <T, U: ReturnValue<T>>queryUnion(vararg queries: QueryScope.() -> U): List<T>{
        var result: U? = null
        val fullQuery = queries.joinToString(" UNION "){ queryBuilder ->
            val scope = QueryScope()
            result = scope.queryBuilder()
            val builtQuery = scope.getString()
            "$builtQuery RETURN ${result!!.getString()} AS r"
        }
        val response = client.graphQuery(name, fullQuery.also { println(it) })
        return response.map { result!!.parse(it.values().first()) }
    }
    fun <T, U: ReturnValue<T>>queryUnionAll(vararg queries: QueryScope.() -> U): List<T>{
        var result: U? = null
        val fullQuery = queries.joinToString(" UNION ALL "){ queryBuilder ->
            val scope = QueryScope()
            result = scope.queryBuilder()
            val builtQuery = scope.getString()
            "$builtQuery RETURN ${result!!.getString()} AS r"
        }
        val response = client.graphQuery(name, fullQuery.also { println(it) })
        return response.map { result!!.parse(it.values().first()) }
    }
    */

}