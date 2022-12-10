package uk.gibby.neo4k.paths.matchable

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.paths.closed.Path8
import uk.gibby.neo4k.paths.open.OpenPath9
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import kotlin.reflect.KFunction

class MatchablePath8<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>, N: DirectionalRelationship<M, O, *>, O: Node<*>>(internal val first: Searchable<A>, internal val firstToSecond: RelationParamMap<B>, internal val second: Searchable<C>, internal val secondToThird: RelationParamMap<D>, internal val third: Searchable<E>, internal val thirdToFourth: RelationParamMap<F>, internal val fourth: Searchable<G>, internal val fourthToFifth: RelationParamMap<H>, internal val fifth: Searchable<I>, internal val fifthToSixth: RelationParamMap<J>, internal val sixth: Searchable<K>, internal val sixthToSeventh: RelationParamMap<L>, internal val seventh: Searchable<M>, internal val seventhToEighth: RelationParamMap<N>, internal val eighth: Searchable<O>, override val ref: String): Matchable<Path8<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>>,
    Creatable<Path8<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>> {
    override fun getReference(): Path8<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O> =
        Path8(
            first.getReference(),
            firstToSecond.getReference(),
            second.getReference(),
            secondToThird.getReference(),
            third.getReference(),
            thirdToFourth.getReference(),
            fourth.getReference(),
            fourthToFifth.getReference(),
            fifth.getReference(),
            fifthToSixth.getReference(),
            sixth.getReference(),
            sixthToSeventh.getReference(),
            seventh.getReference(),
            seventhToEighth.getReference(),
            eighth.getReference(),
            ref
        )

    override fun getSearchString(): String {
        return "${first.getSearchString()}-${firstToSecond.getMatchString()}->${second.getSearchString()}-${secondToThird.getMatchString()}->${third.getSearchString()}-${thirdToFourth.getMatchString()}->${fourth.getSearchString()}-${fourthToFifth.getMatchString()}->${fifth.getSearchString()}-${fifthToSixth.getMatchString()}->${sixth.getSearchString()}-${sixthToSeventh.getMatchString()}->${seventh.getSearchString()}-${seventhToEighth.getMatchString()}->${eighth.getSearchString()}"    }
    operator fun <NEW_R: DirectionalRelationship<O, NEW_N, *>, NEW_N: Node<*>, T: RelationParamMap<NEW_R>>minus(relation: T) = OpenPath9(this, relation)
    operator fun <NEW_R: DirectionalRelationship<O, NEW_N, *>, NEW_N: Node<*>>minus(relation: KFunction<NEW_R>) = this - relation{}
    }