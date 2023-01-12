package uk.gibby.neo4k.queries

import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.clauses.WithAs.Companion.withAs
import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.returns.*
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance

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

