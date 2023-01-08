package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.Creatable
import uk.gibby.neo4k.core.QueryScope


/**
 * Create
 *
 * Represents the 'CREATE' claus from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/create/)
 *
 * @property creatable The nodes or paths to create
 * @constructor Creates a 'CREATE' claus
 */
class Create(private vararg val creatable: Creatable<*>): Clause(){
    override fun getString() = "CREATE ${creatable.joinToString { it.getCreateString() }}"
    companion object{
        /**
         * Create
         *
         * Used to create nodes or paths as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/create/)
         *
         * @param T Type of the reference produced
         * @param U Type of the [Creatable]
         * @param creatable Describes one or more nodes or paths to create
         * @receiver The scope of the query
         * @return The references to the produced nodes or paths
         * @sample [e2e.clauses.Create.createNode]
         * @sample [e2e.clauses.Create.createPath]
         * @sample [e2e.clauses.Create.createPathToReference]
         * @see [QueryScope]
         */
        fun <T, U : Creatable<T>> QueryScope.create(creatable: U): T{
            addStatement(Create(creatable))
            return creatable.getReference()
        }
    }
}

