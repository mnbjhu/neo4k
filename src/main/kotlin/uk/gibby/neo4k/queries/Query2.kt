package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.multiple.MultipleReturn2
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.multiple.Single
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf


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
fun <a, A: Single<a>, b, B: Single<b>, r, R: ReturnValue<r>>query(first: KType, second: KType, builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>{
    return query(TypeProducer(ReturnValue.createDummy(first) as A), TypeProducer(ReturnValue.createDummy(second) as B), builder)
}
inline fun <a, reified A: Single<a>, b, reified B: Single<b>, r, R: ReturnValue<r>>query2(noinline builder: QueryScope.(A, B) -> R): QueryBuilder<Pair<a, b>, MultipleReturn2<a, A, b, B>, r, R>{
    return query(typeOf<A>(), typeOf<B>(), builder)
}
