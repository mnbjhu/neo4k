package uk.gibby.neo4k.returns.graph.entities

import uk.gibby.neo4k.returns.util.ReturnScope

abstract class UnitNode: Node<Unit>() {
    override fun ReturnScope.decode() = Unit
}