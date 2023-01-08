package uk.gibby.neo4k.queries

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import uk.gibby.neo4k.clauses.WithAs
import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import kotlin.reflect.KFunction

class Query0<T, U: ReturnValue<T>>(builder: QueryScope.(EmptyReturn) -> U): Query<T, U, Unit, EmptyReturn>(builder, EmptyReturnInstance){

    companion object{
        fun <T>query(builder: QueryScope.(EmptyReturn) -> ReturnValue<T>): Graph.() -> List<T>{
            val query = Query0(builder)
            return { query.execute(this, Unit) }
        }
    }

    override fun getArgsString(value: Unit): String {
        return ""
    }
}
class Query1<T, U: ReturnValue<T>, a, A: ReturnValue<a>>(builder: QueryScope.(A) -> U, first: TypeProducer<a, A>): Query<T, U, a, A>(builder, (first.inner.createReference("\$arg1") as A)){
    override fun getArgsString(value: a): String {
        return "\"arg1\": ${Json.encodeToString(args.serializer, value)}"
    }

    companion object{
        fun <T, a, A: ReturnValue<a>>query(param: KFunction<A>, builder: QueryScope.(A) -> ReturnValue<T>): Graph.(a) -> List<T>{
            val query = Query1(builder, TypeProducer(param))
            return { p0: a -> query.execute(this, p0) }
        }
    }
}

abstract class Query<T, U: ReturnValue<T>, V, R: ReturnValue<V>>(builder: QueryScope.(R) -> U, val args: R){
    abstract fun getArgsString(value: V): String
    internal val scope = QueryScope()
    private val returnValue = scope.builder(args)
    private val resultParser = if(returnValue is MultipleReturn<*>) ResultSetParser(returnValue.serializer as KSerializer<T?>) else ResultSetParser(SingleParser(returnValue.serializer as KSerializer<T?>).serializer)
    private val hasReturn = returnValue !is EmptyReturn
    private val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
        else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
   fun execute(graph: Graph, value: V): List<T>{
        val response = runBlocking { graph.sendQuery(queryString, getArgsString(value))}
        return if(hasReturn) Json.decodeFromString(resultParser, response.also { println(it) }).first() as List<T> else emptyList()
    }
}
fun <p, P: ReturnValue<p>, r, R: ReturnValue<r>, N, M: ReturnValue<N>>QueryBuilder<p, P, r, R>.with(nextBuilder: QueryScope.(R) -> M): QueryBuilder<p, P, N, M>{
     return QueryBuilder(args) {
         val carried = builder(it)
         val new = if(carried is MultipleReturn<*>) withAs(carried) else using(carried)
         nextBuilder(new)
     }
}
fun <p, P: ReturnValue<p>, r, R: ReturnValue<r>, N, M: ReturnValue<N>>QueryBuilder<p, P, r, R>.with(nextBuilder: QueryScope.(R, P) -> M): QueryBuilder<p, P, N, M>{
     return QueryBuilder(args) {
         val carried = builder(it)
         val new = if(carried is MultipleReturn<*>) withAs(carried) else using(carried)
         nextBuilder(new, it)
     }
}
class QueryBuilder<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val args: P, val builder: QueryScope.(P) -> R){

    fun _build(): NewQuery<p, P, r, R>{
        val scope = QueryScope()
        val returnValue = scope.builder(args)
        val resultParser = if(returnValue is MultipleReturn<*>) ResultSetParser(returnValue.serializer) else ResultSetParser(SingleParser(returnValue.serializer as KSerializer<r?>).serializer)
        val hasReturn = returnValue !is EmptyReturn
        val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
            else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
        return if(args is MultipleReturn<*>) NewQuery(queryString, args.getList().map { it.serializer }, resultParser, hasReturn)
            else NewQuery(queryString, listOf(args.serializer), resultParser, hasReturn)
    }
}
class NewQuery<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val query: String, private val paramSerializers: List<KSerializer<*>>, private val returnSerializer: ResultSetParser<out r?, out KSerializer<out r?>>, private val hasReturn: Boolean){
    fun execute(graph: Graph, args: List<Any?>): List<r?> {
        var count = 0
        val paramString = args.joinToString { "p$count: " + Json.encodeToString(paramSerializers[count++] as KSerializer<Any?>, it) }
        val response = runBlocking { graph.sendQuery(query, paramString) }
        return if(hasReturn) Json.decodeFromString(returnSerializer, response).first() else emptyList()
    }
}

private fun <T: MultipleReturn<*>>QueryScope.withAs(values: T): T{
    val old = values.getList()
    val mapping = old.associateWith { it.createReference(NameCounter.next()) }
    addStatement(WithAs(mapping))
    return values.parseList(mapping.values.toList()) as T
}


fun <r, R: ReturnValue<r>>buildQuery(builder: QueryScope.() -> R): QueryBuilder<Unit, EmptyReturn, r, R> {
    return QueryBuilder(EmptyReturnInstance) { builder() }
}
fun <r, R: ReturnValue<r>>QueryBuilder<Unit, EmptyReturn, r, R>.build(): Graph.() -> List<r>{
    val query = _build()
    return { query.execute(this, listOf()) as List<r> }
}

fun <a, A: ReturnValue<a>, r, R: ReturnValue<r>>buildQuery()