package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.multiple.Single
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: TypeProducer<a, A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R>{
    val param0 = first.inner.createReference("\$p0") as A
    return QueryBuilder(param0){
        builder(it)
    }
}
inline fun <a, reified A: Single<a>, r, R: ReturnValue<r>>query1(noinline builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R>{
    return query(typeOf<A>(), builder)
}
fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: KType, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R>{
    val type = TypeProducer(ReturnValue.createDummy(first, "dummy") as A)
    return query(type, builder)

}
fun <a, A: Single<a>, r, R: ReturnValue<r>>query(first: KFunction<A>, builder: QueryScope.(A) -> R): QueryBuilder<a, A, r, R> = query(TypeProducer(first), builder)
fun <a, A: Single<a>, r, R: ReturnValue<r>>QueryBuilder<a,A,r, R>.build(): Graph.(a) -> List<r>{
    val query = _build()
    return { query.execute(this, listOf(it)) as List<r> }
}
