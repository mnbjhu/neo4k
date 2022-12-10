package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath7
import uk.gibby.neo4k.paths.matchable.MatchablePath8
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath8<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>, N: DirectionalRelationship<M, O, *>, O: Node<*>>(private val prev: MatchablePath7<A, B, C, D, E, F, G, H, I, J, K, L, M>, internal val seventhToEighth: RelationParamMap<N>){operator fun minus(node: NodeParamMap<O>) =
    MatchablePath8(
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
        seventhToEighth,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath8(
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
            seventhToEighth,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath8(
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
            seventhToEighth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}