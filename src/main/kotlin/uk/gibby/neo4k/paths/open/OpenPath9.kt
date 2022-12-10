package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath8
import uk.gibby.neo4k.paths.matchable.MatchablePath9
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath9<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>, N: DirectionalRelationship<M, O, *>, O: Node<*>, P: DirectionalRelationship<O, Q, *>, Q: Node<*>>(private val prev: MatchablePath8<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O>, internal val eighthToNinth: RelationParamMap<P>) {
    operator fun minus(node: NodeParamMap<Q>) =
        MatchablePath9(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            prev.fifthToSixth,
            prev.sixth,
            prev.sixthToSeventh,
            prev.seventh,
            prev.seventhToEighth,
            prev.eighth,
            eighthToNinth,
            node,
            prev.ref
        )

    operator fun minus(nodeRef: C) =
        MatchablePath9(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            prev.fifthToSixth,
            prev.sixth,
            prev.sixthToSeventh,
            prev.seventh,
            prev.seventhToEighth,
            prev.eighth,
            eighthToNinth,
            NodeReference(nodeRef),
            prev.ref
        )

    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath9(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            prev.fifthToSixth,
            prev.sixth,
            prev.sixthToSeventh,
            prev.seventh,
            prev.seventhToEighth,
            prev.eighth,
            eighthToNinth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}