package uk.gibby.neo4k.core

import uk.gibby.neo4k.returns.ReturnValue.Companion.createReference
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.Relationship
import kotlin.reflect.KClass
import kotlin.reflect.KType

class RelationParamMap<out U: Relationship<*, *, *>>(private val refType: KType, private val ref: String = NameCounter.next()): ParamMap<U>(refType){
    fun getMatchString(): String {
        val paramString = if(entries.isEmpty()) "" else "{${entries.joinToString { "${it.first}:${it.second.getString()}" }}}"
        val className = (type.classifier as KClass<*>).simpleName
        return "[$ref:$className$paramString]"
    }
    fun getReference(): U {
        return createReference(refType, ref) as U
    }
}