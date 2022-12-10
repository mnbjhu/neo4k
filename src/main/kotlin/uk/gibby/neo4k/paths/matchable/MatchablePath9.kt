package uk.gibby.neo4k.paths.matchable

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.paths.closed.Path9
import uk.gibby.neo4k.paths.open.OpenPath10
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import kotlin.reflect.KFunction

class MatchablePath9<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>, N: DirectionalRelationship<M, O, *>, O: Node<*>, P: DirectionalRelationship<O, Q, *>, Q: Node<*>>(internal val first: Searchable<A>, internal val firstToSecond: RelationParamMap<B>, internal val second: Searchable<C>, internal val secondToThird: RelationParamMap<D>, internal val third: Searchable<E>, internal val thirdToFourth: RelationParamMap<F>, internal val fourth: Searchable<G>, internal val fourthToFifth: RelationParamMap<H>, internal val fifth: Searchable<I>, internal val fifthToSixth: RelationParamMap<J>, internal val sixth: Searchable<K>, internal val sixthToSeventh: RelationParamMap<L>, internal val seventh: Searchable<M>, internal val seventhToEighth: RelationParamMap<N>, internal val eighth: Searchable<O>, internal val eighthToNinth: RelationParamMap<P>, internal val ninth: Searchable<Q>, override val ref: String): Matchable<Path9<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>>,
    Creatable<Path9<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>> {
    override fun getReference(): Path9<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q> =
        Path9(
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
            eighthToNinth.getReference(),
            ninth.getReference(),
            ref
        )

    override fun getSearchString(): String {
        return "${first.getSearchString()}-${firstToSecond.getMatchString()}->${second.getSearchString()}-${secondToThird.getMatchString()}->${third.getSearchString()}-${thirdToFourth.getMatchString()}->${fourth.getSearchString()}-${fourthToFifth.getMatchString()}->${fifth.getSearchString()}-${fifthToSixth.getMatchString()}->${sixth.getSearchString()}-${sixthToSeventh.getMatchString()}->${seventh.getSearchString()}-${seventhToEighth.getMatchString()}->${eighth.getSearchString()}-${eighthToNinth.getMatchString()}->${ninth.getSearchString()}"    }
    operator fun <NEW_R: DirectionalRelationship<Q, NEW_N, *>, NEW_N: Node<*>, T: RelationParamMap<NEW_R>>minus(relation: T) = OpenPath10(this, relation)
    operator fun <NEW_R: DirectionalRelationship<Q, NEW_N, *>, NEW_N: Node<*>>minus(relation: KFunction<NEW_R>) = this - relation{}
    }