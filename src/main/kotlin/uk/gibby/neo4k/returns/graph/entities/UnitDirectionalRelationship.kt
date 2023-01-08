package uk.gibby.neo4k.returns.graph.entities

import kotlinx.serialization.KSerializer
import uk.gibby.neo4k.returns.util.ReturnScope

abstract class UnitDirectionalRelationship<A: Node<*>, B: Node<*>>: DirectionalRelationship<A, B, Unit>() {
    override val serializer: KSerializer<Unit>
        get() = TODO("Not yet implemented")
    override fun ReturnScope.decode() = Unit
}
abstract class UnitNonDirectionalRelationship<A: Node<*>>: NonDirectionalRelationship<A, Unit>() {
    override val serializer: KSerializer<Unit>
        get() = TODO("Not yet implemented")
    override fun ReturnScope.decode() = Unit
}