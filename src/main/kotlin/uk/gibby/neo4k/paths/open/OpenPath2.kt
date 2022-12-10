package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.core.Searchable
import uk.gibby.neo4k.paths.matchable.*
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath2<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>(
    private val first: Searchable<A>,
    private val firstToSecond: RelationParamMap<B>
){
    infix fun `→`(node: NodeParamMap<C>) =
        MatchablePath2(first, firstToSecond, node, NameCounter.next())
    infix fun `→`(nodeRef: C) =
        MatchablePath2(first, firstToSecond, NodeReference(nodeRef), NameCounter.next())
    infix fun `→`(nodeType: KFunction<C>) =
        MatchablePath2(first, firstToSecond, NodeParamMap(nodeType.returnType), NameCounter.next())
}

