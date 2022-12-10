package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath5
import uk.gibby.neo4k.paths.matchable.MatchablePath6
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath6<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>>(private val prev: MatchablePath5<A, B, C, D, E, F, G, H, I>, internal val fifthToSixth: RelationParamMap<J>){operator fun minus(node: NodeParamMap<K>) =
    MatchablePath6(
        prev.first,
        prev.firstToSecond,
        prev.second,
        prev.secondToThird,
        prev.third,
        prev.thirdToFourth,
        prev.fourth,
        prev.fourthToFifth,
        prev.fifth,
        fifthToSixth,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath6(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            fifthToSixth,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath6(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            fifthToSixth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}