package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.queries.Query
import uk.gibby.neo4k.returns.empty.EmptyReturn

class Call(private val innerQuery: Query<Unit, EmptyReturn, *, *>): Clause(){
    override fun getString(): String {
        return "CALL { WITH * ${innerQuery.query} }"
    }
}