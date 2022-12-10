package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath6
import uk.gibby.neo4k.paths.matchable.MatchablePath7
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath7<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>>(private val prev: MatchablePath6<A, B, C, D, E, F, G, H, I, J, K>, internal val sixthToSeventh: RelationParamMap<L>){operator fun minus(node: NodeParamMap<M>) =
    MatchablePath7(
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
        sixthToSeventh,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath7(
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
            sixthToSeventh,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath7(
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
            sixthToSeventh,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}