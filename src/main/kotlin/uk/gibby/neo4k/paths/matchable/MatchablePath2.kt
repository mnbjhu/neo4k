package uk.gibby.neo4k.paths.matchable

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.paths.closed.*
import uk.gibby.neo4k.paths.open.OpenPath3
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import kotlin.reflect.KFunction

class MatchablePath2<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>(
    internal val first: Searchable<A>,
    internal val firstToSecond: RelationParamMap<B>,
    internal val second: Searchable<C>,
    override val ref: String,
): Matchable<Path2<A, B, C>>, Creatable<Path2<A, B, C>> {
    override fun getReference(): Path2<A, B, C> =
        Path2(first.getReference(), firstToSecond.getReference(), second.getReference(), ref)

    override fun getSearchString(): String {
        return "${first.getSearchString()}-${firstToSecond.getMatchString()}->${second.getSearchString()}"
    }
    operator fun <NEW_R: DirectionalRelationship<C, NEW_N, *>, NEW_N: Node<*>, T: RelationParamMap<NEW_R>>minus(relation: T) = OpenPath3(this, relation)
    operator fun <NEW_R: DirectionalRelationship<C, NEW_N, *>, NEW_N: Node<*>>minus(relation: KFunction<NEW_R>) = this - relation{}

}

