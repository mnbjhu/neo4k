package uk.gibby.neo4k.returns.graph.entities

import uk.gibby.neo4k.returns.util.ReturnScope

abstract class UnitDirectionalRelationship<A: Node<*>, B: Node<*>>: DirectionalRelationship<A, B, Unit>() {
    override fun ReturnScope.decode() = Unit
}
abstract class UnitNonDirectionalRelationship<A: Node<*>>: NonDirectionalRelationship<A, Unit>() {
    override fun ReturnScope.decode() = Unit
}