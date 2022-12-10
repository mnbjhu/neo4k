package uk.gibby.neo4k.returns.graph.entities

import org.neo4j.driver.internal.value.RelationshipValue
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

abstract class DirectionalRelationship<out A: Node<*>, out B: Node<*>, T>: Relationship<A, B, T>()


abstract class Relationship<out A: Node<*>, out B: Node<*>, T>: Entity<T>(){
    override fun parse(value: Any?): T {
        val nodeData = (value as RelationshipValue)
        val map = this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .associate {
                it.name to nodeData[it.name]
                //it.name to nodeData.getProperty(it.name).value
            }
        return ReturnScope(map).decode()
    }
}

abstract class NonDirectionalRelationship<out A: Node<*>, T>: Relationship<A, A, T>()