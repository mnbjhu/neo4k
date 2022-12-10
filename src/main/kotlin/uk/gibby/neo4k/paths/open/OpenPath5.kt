package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath4
import uk.gibby.neo4k.paths.matchable.MatchablePath5
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath5<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>>(private val prev: MatchablePath4<A, B, C, D, E, F, G>, internal val fourthToFifth: RelationParamMap<H>){operator fun minus(node: NodeParamMap<I>) =
    MatchablePath5(
        prev.first,
        prev.firstToSecond,
        prev.second,
        prev.secondToThird,
        prev.third,
        prev.thirdToFourth,
        prev.fourth,
        fourthToFifth,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath5(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            fourthToFifth,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath5(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            fourthToFifth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}