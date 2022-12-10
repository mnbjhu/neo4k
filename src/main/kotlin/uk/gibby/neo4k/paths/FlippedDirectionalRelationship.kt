package uk.gibby.neo4k.paths

import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.ReturnScope

class FlippedDirectionalRelationship<A: Node<*>, B: Node<*>, T>(val inner: DirectionalRelationship<A, B, T>): DirectionalRelationship<B, A, T>() {
    override fun ReturnScope.decode(): T = with(inner){ decode() }
}