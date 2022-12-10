package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.graph.entities.Entity


/**
 * Delete
 *
 * Represents the 'DELETE' claus from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/delete/)
 *
 * @property toDelete The nodes or relations to delete
 * @constructor Creates a 'DELETE' claus
 */
class Delete(private val toDelete: List<Entity<*>>): Claus() {
    override fun getString(): String {
        return "DELETE ${toDelete.joinToString { it.getString() }}"
    }
    companion object{

        /**
         * Delete
         *
         * Used to delete nodes or relations as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/delete/)
         *
         * @param toDelete The nodes or relations to delete
         * @return An empty return so queries can end with this claus
         * @sample [e2e.clauses.Delete.deleteNode]
         * @sample [e2e.clauses.Delete.deleteRelation]
         * @see [QueryScope]
         */
        fun QueryScope.delete(vararg toDelete: Entity<*>) = EmptyReturnInstance
            .also { addStatement(Delete(toDelete.toList())) }
    }
}
class DetachDelete(private val toDelete: List<Entity<*>>): Claus() {
    override fun getString(): String {
        return "DETACH DELETE ${toDelete.joinToString { it.getString() }}"
    }
    companion object{

        /**
         * Delete
         *
         * Used to delete nodes or relations as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/delete/)
         *
         * @param toDelete The nodes or relations to delete
         * @return An empty return so queries can end with this claus
         * @sample [e2e.clauses.Delete.deleteNode]
         * @sample [e2e.clauses.Delete.deleteRelation]
         * @see [QueryScope]
         */
        fun QueryScope.detachDelete(vararg toDelete: Entity<*>) = EmptyReturnInstance
            .also { addStatement(DetachDelete(toDelete.toList())) }
    }
}