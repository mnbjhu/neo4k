package uk.gibby.neo4k.returns.util

import uk.gibby.neo4k.core.Searchable
import uk.gibby.neo4k.returns.graph.entities.Node

class NodeReference<U: Node<*>>(private val node: U) : Searchable<U> {
    override val ref: String = node.getString()
    override fun getReference(): U {
        return node
    }

    override fun getSearchString(): String {
        return "(${node.getString()})"
    }
}