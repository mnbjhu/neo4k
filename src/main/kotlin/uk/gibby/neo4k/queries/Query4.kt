package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.multiple.MultipleReturn4
import uk.gibby.neo4k.returns.multiple.Single
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun <
        a, A: Single<a>,
        b, B: Single<b>,
        c, C: Single<c>,
        d, D: Single<d>,
        r, R: ReturnValue<r>
        >query(first: TypeProducer<a, A>, second: TypeProducer<b, B>, third: TypeProducer<c, C>, forth: TypeProducer<d, D>, builder: QueryScope.(A, B, C, D) -> R): QueryBuilder<MultipleReturn4.Vec<a, b, c, d>, MultipleReturn4<a, A, b, B, c, C, d, D>, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    val param1 = second.inner.createReference("\$p1") as B
    val param2 = third.inner.createReference("\$p2") as C
    val param3 = forth.inner.createReference("\$p3") as D
    return QueryBuilder(MultipleReturn4(param0, param1, param2, param3)) {
        builder(it.first, it.second, it.third, it.forth)
    }
}

fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, d, D: Single<d>, r, R: ReturnValue<r>>query(first: KType, second: KType, third: KType, forth: KType, builder: QueryScope.(A, B, C, D) -> R): QueryBuilder<MultipleReturn4.Vec<a, b, c, d>, MultipleReturn4<a, A, b, B, c, C, d, D>, r, R>{
    return query(TypeProducer(ReturnValue.createDummy(first) as A), TypeProducer(ReturnValue.createDummy(second) as B) ,TypeProducer(ReturnValue.createDummy(third) as C),TypeProducer(ReturnValue.createDummy(forth) as D), builder)
}
inline fun <a, reified A: Single<a>, b, reified B: Single<b>, c, reified C: Single<c>, d, reified D: Single<d>, r, R: ReturnValue<r>>query4(noinline builder: QueryScope.(A, B, C, D) -> R): QueryBuilder<MultipleReturn4.Vec<a, b, c, d>, MultipleReturn4<a, A, b, B, c, C, d, D>, r, R>{
    return query(typeOf<A>(), typeOf<B>(), typeOf<C>(), typeOf<D>(), builder)
}
fun <a, A: Single<a>, b, B: Single<b>, c, C: Single<c>, d, D: Single<d>, r, R: ReturnValue<r>> QueryBuilder<MultipleReturn4.Vec<a, b, c, d>, MultipleReturn4<a, A, b, B, c, C, d, D>, r, R>.build(): Graph.(a, b, c, d) -> List<r> {
    val query = _build()
    return { first, second, third, fourth -> query.execute(this, listOf(first, second, third, fourth)) as List<r> }
}
