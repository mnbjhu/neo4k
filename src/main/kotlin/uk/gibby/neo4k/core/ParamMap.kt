package uk.gibby.neo4k.core


import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.ReturnValue.Companion.createDummy
import uk.gibby.neo4k.returns.ReturnValue.Companion.createInstance
import uk.gibby.neo4k.returns.graph.entities.Entity
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.Relationship
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KType


sealed class ParamMap<out U: Entity<*>>(protected val type: KType){
    val entries: MutableList<Pair<String, ReturnValue<*>>> = mutableListOf()
    operator fun <U: ReturnValue<*>>set(attribute: KProperty<U>, value: U){
        entries.add(attribute.name to value)
    }
    operator fun <T, U: ReturnValue<T>>set(attribute: KProperty<U>, value: T){
        entries.add(attribute.name to createInstance(attribute.returnType, value))
    }

}

operator fun <U: Node<*>> KFunction<U>.invoke(nodeBuilder: U.(NodeParamMap<U>) -> Unit): NodeParamMap<U> {
    val type = returnType
    val dummy = createDummy(type) as U
    return with(dummy){ NodeParamMap<U>(type).also { nodeBuilder(it) } }
}
operator fun <U: Relationship<*, *, *>> KFunction<U>.invoke(relationBuilder: U.(RelationParamMap<U>) -> Unit): RelationParamMap<U> {
    val type = returnType
    val dummy = createDummy(type) as U
    return with(dummy){ RelationParamMap<U>(type).also { relationBuilder(it) } }
}
