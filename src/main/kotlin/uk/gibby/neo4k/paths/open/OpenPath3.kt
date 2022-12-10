package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath2
import uk.gibby.neo4k.paths.matchable.MatchablePath3
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath3<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>>(private val prev: MatchablePath2<A, B, C>, internal val secondToThird: RelationParamMap<D>){operator fun minus(node: NodeParamMap<E>) =
    MatchablePath3(prev.first, prev.firstToSecond, prev.second, secondToThird, node, prev.ref)
    operator fun minus(nodeRef: C) =
        MatchablePath3(prev.first, prev.firstToSecond, prev.second, secondToThird, NodeReference(nodeRef), prev.ref)
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath3(
            prev.first,
            prev.firstToSecond,
            prev.second,
            secondToThird,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}