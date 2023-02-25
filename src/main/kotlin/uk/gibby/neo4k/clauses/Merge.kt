package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.Creatable
import uk.gibby.neo4k.core.QueryScope


/**
 * Create
 *
 * Represents the 'CREATE' clause from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/create/)
 *
 * @property creatable The nodes or paths to create
 * @constructor Creates a 'CREATE' clause
 */
class Merge(private vararg val creatable: Creatable<*>): Clause(){
    override fun getString() = "MERGE ${creatable.joinToString { it.getCreateString() }}"
    companion object{
        /**
         * Create
         *
         * Used to create nodes or paths as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/merge/)
         *
         * @param T Type of the reference produced
         * @param U Type of the [Creatable]
         * @param creatable Describes one node or path to merge
         * @receiver The scope of the query
         * @return The references to the produced nodes or paths
         * @see [QueryScope]
         */
        fun <T, U : Creatable<T>> QueryScope.merge(creatable: U): T{
            addStatement(Create(creatable))
            return creatable.getReference()
        }
    }
}
