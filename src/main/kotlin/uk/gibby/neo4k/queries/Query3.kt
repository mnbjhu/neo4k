package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.multiple.MultipleReturn3
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.multiple.Single
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun <
    a, A: Single<a>,
    b, B: Single<b>,
    c, C: Single<c>,
    r, R: ReturnValue<r>
>query(first: TypeProducer<a, A>, second: TypeProducer<b, B>, third: TypeProducer<c, C>, builder: QueryScope.(A, B, C) -> R): QueryBuilder<Triple<a, b, c>, MultipleReturn3<a, A, b, B, c, C>, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    val param1 = second.inner.createReference("\$p1") as B
    val param2 = third.inner.createReference("\$p2") as C
    return QueryBuilder(MultipleReturn3(param0, param1, param2)){
        builder(it.first, it.second, it.third)
    }
}
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, second: TypeProducer<b, B>, third: KFunction<C>, builder: QueryScope.(A, B, C) -> R) = query(first, second, TypeProducer(third), builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, second: KFunction<B>, third: TypeProducer<c, C>, builder: QueryScope.(A, B, C) -> R) = query(first, TypeProducer(second), third, builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, second: KFunction<B>, third: KFunction<C>, builder: QueryScope.(A, B, C) -> R) = query(first, TypeProducer(second), TypeProducer(third), builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: KFunction<A>, second: TypeProducer<b, B>, third: TypeProducer<c, C>, builder: QueryScope.(A, B, C) -> R) = query(TypeProducer(first), second, third, builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: KFunction<A>, second: TypeProducer<b, B>, third: KFunction<C>, builder: QueryScope.(A, B, C) -> R) = query(TypeProducer(first), second, TypeProducer(third), builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: KFunction<A>, second: KFunction<B>, third: TypeProducer<c, C>, builder: QueryScope.(A, B, C) -> R) = query(TypeProducer(first), TypeProducer(second), third, builder)
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: KFunction<A>, second: KFunction<B>, third: KFunction<C>, builder: QueryScope.(A, B, C) -> R) = query(TypeProducer(first), TypeProducer(second), TypeProducer(third), builder)

fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, r, R: ReturnValue<r>>query(first: KType, second: KType, third: KType, builder: QueryScope.(A, B, C) -> R): QueryBuilder<Triple<a, b, c>, MultipleReturn3<a, A, b, B, c, C>, r, R>{
    return query(TypeProducer(ReturnValue.createDummy(first) as A), TypeProducer(ReturnValue.createDummy(second) as B) ,TypeProducer(ReturnValue.createDummy(third) as C), builder)
}
inline fun <a, reified A: Single<a>, b, reified B: Single<b>, c, reified C: Single<c>, r, R: ReturnValue<r>>query3(noinline builder: QueryScope.(A, B, C) -> R): QueryBuilder<Triple<a, b, c>, MultipleReturn3<a, A, b, B, c, C>, r, R>{
    return query(typeOf<A>(), typeOf<B>(), typeOf<C>(), builder)
}