package uk.gibby.neo4k.paths.matchable

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.paths.closed.Path5
import uk.gibby.neo4k.paths.open.OpenPath6
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import kotlin.reflect.KFunction

class MatchablePath5<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>>(internal val first: Searchable<A>, internal val firstToSecond: RelationParamMap<B>, internal val second: Searchable<C>, internal val secondToThird: RelationParamMap<D>, internal val third: Searchable<E>, internal val thirdToFourth: RelationParamMap<F>, internal val fourth: Searchable<G>, internal val fourthToFifth: RelationParamMap<H>, internal val fifth: Searchable<I>, override val ref: String): Matchable<Path5<A, B, C, D, E, F, G, H, I>>,
    Creatable<Path5<A, B, C, D, E, F, G, H, I>> {
    override fun getReference(): Path5<A, B, C, D, E, F, G, H, I> =
        Path5(
            first.getReference(),
            firstToSecond.getReference(),
            second.getReference(),
            secondToThird.getReference(),
            third.getReference(),
            thirdToFourth.getReference(),
            fourth.getReference(),
            fourthToFifth.getReference(),
            fifth.getReference(),
            ref
        )

    override fun getSearchString(): String {
        return "${first.getSearchString()}-${firstToSecond.getMatchString()}->${second.getSearchString()}-${secondToThird.getMatchString()}->${third.getSearchString()}-${thirdToFourth.getMatchString()}->${fourth.getSearchString()}-${fourthToFifth.getMatchString()}->${fifth.getSearchString()}"    }
    operator fun <NEW_R: DirectionalRelationship<I, NEW_N, *>, NEW_N: Node<*>, T: RelationParamMap<NEW_R>>minus(relation: T) = OpenPath6(this, relation)
    operator fun <NEW_R: DirectionalRelationship<I, NEW_N, *>, NEW_N: Node<*>>minus(relation: KFunction<NEW_R>) = this - relation{}
    }