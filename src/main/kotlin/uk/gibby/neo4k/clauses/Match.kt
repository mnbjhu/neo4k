package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.Matchable
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.returns.graph.entities.Node
import kotlin.reflect.KFunction

/**
 * Match
 *
 * Represents the 'MATCH' claus from the CypherQL
 *
 * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/match/)
 *
 * @property matchable The nodes or paths to create
 * @constructor Creates a 'CREATE' claus
 */
class Match(private vararg val matchable: Matchable<*>): Clause(){
    override fun getString() = "MATCH ${matchable.joinToString { it.getMatchString() }}"
    companion object{

        /**
         * Match
         *
         * Used to match a node or path as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/match/)
         *
         * @param T Type of the reference produced
         * @param U Type of the [Matchable]
         * @param matchable Describes a node or path to match
         * @receiver The scope of the query
         * @return The reference to the matched node or path
         * @see [QueryScope]
         * @sample [e2e.clauses.Match.matchOneNodeFiltered]
         * @sample [e2e.clauses.Match.matchPath]
         */
        fun <T, U : Matchable<T>> QueryScope.match(matchable: U): T{
            addStatement(Match(matchable))
            return matchable.getReference()
        }

        /**
         * Match
         *
         * Used to match a node or path as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/match/)
         * @receiver The scope of the query
         * @param U Type of the node being matched and the reference produced
         * @param node Constructor reference of the node to match
         * @return The reference to the matched node
         * @see [QueryScope]
         * @sample [e2e.clauses.Match.matchOneNode]
         */
        inline fun <reified U : Node<*>> QueryScope.match(node: KFunction<U>) = match(node{})

        /**
         * Match
         *
         * Used to match two nodes or paths as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/match/)
         * @receiver The scope of the query
         * @param T Type of the first reference produced
         * @param U Type of the first [Matchable]
         * @param A Type of the second reference produced
         * @param B Type of the second [Matchable]
         * @param first Describes the first node or path to match
         * @param second Describes the second node or path to match
         * @return Pair of references to the matched nodes or paths
         * @see [QueryScope]
         * @sample [e2e.clauses.Match.matchTwoNodesFiltered]
         */
        fun <T, U : Matchable<T>, A, B : Matchable<A>> QueryScope.match(first: U, second: B): Pair<T, A>{
            addStatement(Match(first, second))
            return first.getReference() to second.getReference()
        }

        /**
         * Match
         *
         * Used to match two nodes or paths as part of a graph query
         *
         * [Neo4j Cypher Manual](https://neo4j.com/docs/cypher-manual/current/clauses/match/)
         * @receiver The scope of the query
         * @param U Type of the first node being matched and the reference produced
         * @param B Type of the second node being matched and the reference produced
         * @param first Constructor reference of the first node to match
         * @param second Constructor reference of the second node to match
         * @return Pair of references to the matched nodes or paths
         * @see [QueryScope]
         * @sample [e2e.clauses.Match.matchTwoNodes]
         */
        inline fun <reified U : Node<*>, reified B: Node<*>> QueryScope.match(first: KFunction<U>, second: KFunction<B>) =
            match(first{}, second{})
    }
}