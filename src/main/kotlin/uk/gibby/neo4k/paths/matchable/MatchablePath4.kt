package uk.gibby.neo4k.paths.matchable

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.paths.closed.Path4
import uk.gibby.neo4k.paths.open.OpenPath5
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import kotlin.reflect.KFunction

class MatchablePath4<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>>(internal val first: Searchable<A>, internal val firstToSecond: RelationParamMap<B>, internal val second: Searchable<C>, internal val secondToThird: RelationParamMap<D>, internal val third: Searchable<E>, internal val thirdToFourth: RelationParamMap<F>, internal val fourth: Searchable<G>, override val ref: String): Matchable<Path4<A, B, C, D, E, F, G>>,
    Creatable<Path4<A, B, C, D, E, F, G>> {
    override fun getReference(): Path4<A, B, C, D, E, F, G> =
        Path4(
            first.getReference(),
            firstToSecond.getReference(),
            second.getReference(),
            secondToThird.getReference(),
            third.getReference(),
            thirdToFourth.getReference(),
            fourth.getReference(),
            ref
        )

    override fun getSearchString(): String {
        return "${first.getSearchString()}-${firstToSecond.getMatchString()}->${second.getSearchString()}-${secondToThird.getMatchString()}->${third.getSearchString()}-${thirdToFourth.getMatchString()}->${fourth.getSearchString()}"    }

    operator fun <NEW_R: DirectionalRelationship<G, NEW_N, *>, NEW_N: Node<*>, T: RelationParamMap<NEW_R>>minus(relation: T) = OpenPath5(this, relation)
    operator fun <NEW_R: DirectionalRelationship<G, NEW_N, *>, NEW_N: Node<*>>minus(relation: KFunction<NEW_R>) = this - relation{}
}