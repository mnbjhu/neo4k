package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.Single
import kotlin.reflect.KFunction

fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    return QueryBuilder(param0){
        builder(it)
    }
}
fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: KFunction<A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R> = query(
    TypeProducer(first), builder)
fun <a, A: Single<a>, r, R: ReturnValue<r>>QueryBuilder<a,A,r, R>.build(): Graph.(a) -> List<r>{
    val query = _build()
    return { query.execute(this, listOf(it)) as List<r> }
}