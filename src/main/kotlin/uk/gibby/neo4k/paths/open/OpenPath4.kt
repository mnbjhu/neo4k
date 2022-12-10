package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath3
import uk.gibby.neo4k.paths.matchable.MatchablePath4
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath4<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>>(private val prev: MatchablePath3<A, B, C, D, E>, internal val thirdToFourth: RelationParamMap<F>){operator fun minus(node: NodeParamMap<G>) =
    MatchablePath4(
        prev.first,
        prev.firstToSecond,
        prev.second,
        prev.secondToThird,
        prev.third,
        thirdToFourth,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath4(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            thirdToFourth,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath4(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            thirdToFourth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}