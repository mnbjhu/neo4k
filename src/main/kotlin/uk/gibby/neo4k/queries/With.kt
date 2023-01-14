package uk.gibby.neo4k.queries

import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.clauses.WithAs.Companion.withAs
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.multiple.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue

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
