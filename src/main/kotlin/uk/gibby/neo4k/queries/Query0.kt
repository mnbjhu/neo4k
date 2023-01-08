package uk.gibby.neo4k.queries

import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.clauses.WithAs.Companion.withAs
import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.returns.*
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import kotlin.reflect.KFunction

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




fun <r, R: ReturnValue<r>>query(builder: QueryScope.() -> R): QueryBuilder<Unit, EmptyReturn, r, R> {
    return QueryBuilder(EmptyReturnInstance) { builder() }
}
fun <r, R: ReturnValue<r>>QueryBuilder<Unit, EmptyReturn, r, R>.build(): Graph.() -> List<r>{
    val query = _build()
    return { query.execute(this, listOf()) as List<r> }
}

fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    return QueryBuilder(param0){
        builder(it)
    }
}
fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: KFunction<A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R> = query(TypeProducer(first), builder)
fun <a, A: Single<a>, r, R: ReturnValue<r>>QueryBuilder<a,A,r, R>.build(): Graph.(a) -> List<r>{
    val query = _build()
    return { query.execute(this, listOf(it)) as List<r> }
}
fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, second: TypeProducer<b, B>, builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    val param1 = second.inner.createReference("\$p1") as B
    return QueryBuilder(MultipleReturn2(param0, param1)){
        builder(it.first, it.second)
    }
}
fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>query(first: KFunction<A>, second: TypeProducer<b, B>, builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>
    = query(TypeProducer(first), second, builder)

fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>query(first: KFunction<A>, second:KFunction<B>, builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>
        = query(TypeProducer(first), TypeProducer(second), builder)
fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, second: KFunction<B>, builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>
        = query(first, TypeProducer(second), builder)

fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>.build(): Graph.(a, b) -> List<r>{
    val query = _build()
    return { first, second -> query.execute(this, listOf(first, second)) as List<r> }
}

