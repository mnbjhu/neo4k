package uk.gibby.neo4k.core


import uk.gibby.neo4k.returns.ReturnValue.Companion.createReference
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.superclasses


class NodeParamMap<U: Node<*>>(
    private val refType: KType,
    override val ref: String = NameCounter.next()
): ParamMap<U>(refType), Searchable<U> {
    override fun getSearchString(): String {
        val paramString = if(entries.isEmpty()) "" else "{${entries.joinToString { "${it.first}:${it.second}" }}}"
        val labels = listOf(type.classifier as KClass<*>) + (type.classifier as KClass<*>).superclasses
            .takeWhile { it != Node::class && it != UnitNode::class }
        return "($ref:${labels.joinToString(":"){ it.simpleName!! }}$paramString)"
    }
    override fun getReference(): U {
        return createReference(refType, ref) as U
    }
}