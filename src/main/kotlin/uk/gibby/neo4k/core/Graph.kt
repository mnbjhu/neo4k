package uk.gibby.neo4k.core

import org.neo4j.driver.*
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
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
    private val name: String,
    host: String,
    port: Int = 7687,
    password: String? = null
) {
    private val client = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("test", "test"))
        .apply { session().executeWrite{ it.run("CREATE DATABASE $name IF NOT EXISTS") } }

    /**
     * Query
     *
     * @param T The return type of the query
     * @param queryBuilder A scope for creating a query using clauses defined on [QueryScope]
     * @return Query result in the form of a list of [T]
     */
    fun <T>query(queryBuilder: QueryScope.() -> ReturnValue<T>): List<T>{
        val scope = QueryScope()
        val result = scope.queryBuilder()
        val queryStart = scope.getString()
        val queryEnd = scope.getAfterString()
        return when(result){
            is EmptyReturn -> {
                client.session(SessionConfig.forDatabase(name)).executeWrite {
                    val query = Query("$queryStart $queryEnd")
                    it.run(query)
                }
                emptyList()
            }
            else -> {
                client.session(SessionConfig.forDatabase(name)).executeWrite{
                    val query = Query("$queryStart RETURN ${result.getString()} $queryEnd".also { println(it) })
                    if(result is MultipleReturn) it.run(query).list { result.parse(it.values()) }
                        else { it.run(query).list { result.parse(it.values().first()) } }
                }
            }
    }
    }/*
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

    /**
     * Delete
     *
     * Deletes the graph (graph by name: [name])
     */
    fun delete(){
        client.session(SessionConfig.forDatabase(name)).executeWrite{
            val query = Query("MATCH (n) DETACH DELETE n")
            it.run(query)
        }
    }
}