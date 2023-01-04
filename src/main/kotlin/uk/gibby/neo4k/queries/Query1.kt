package uk.gibby.neo4k.queries

import org.neo4j.driver.SessionConfig
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import kotlin.reflect.KFunction
/*
class Query1<T, a, A: ReturnValue<a>>(first: A, builder: QueryScope.(A) -> ReturnValue<T>) {
    private val scope = QueryScope()
    private val returnValue = scope.builder(first.createReference("\$first") as A)
    val query = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
        else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
    fun execute(graph: Graph, first: a): List<T> = if(returnValue is EmptyReturn){
        graph.driver.session(SessionConfig.forDatabase(graph.name)).executeWrite{
            it.run(query, mapOf("first" to first))
        }
        emptyList()
    } else {
        graph.driver.session(SessionConfig.forDatabase(graph.name)).executeWrite{
            if(returnValue is MultipleReturn) it.run(query).list { returnValue.parse(it.values()) }
            else { it.run(query, mapOf("first" to first)).list { returnValue.parse(it.values().first()) } }
        }
    }
    companion object{
        fun <T, a, A: ReturnValue<a>>query(firstType: KFunction<A>, builder: QueryScope.(A) -> ReturnValue<T>): Graph.(a) -> List<T>{

            val query = Query1(ReturnValue.createDummy(firstType), builder)
            return { first -> query.execute(this, first) }
        }
        fun <T, a, A: ReturnValue<a>>query(firstType: TypeProducer<a, A>, builder: QueryScope.(A) -> ReturnValue<T>): Graph.(a) -> List<T>{
            val query = Query1(firstType.inner, builder)
            return { first -> query.execute(this, first) }
        }
    }
}*/