package uk.gibby.neo4k.queries

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.returns.*
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance




fun <r, R: ReturnValue<r>>query(builder: QueryScope.() -> R): QueryBuilder<Unit, EmptyReturn, r, R> {
    return QueryBuilder(EmptyReturnInstance) { builder() }
}
fun <r, R: ReturnValue<r>>QueryBuilder<Unit, EmptyReturn, r, R>.build(): Graph.() -> List<r>{
    val query = _build()
    return { query.execute(this, listOf()) as List<r> }
}


