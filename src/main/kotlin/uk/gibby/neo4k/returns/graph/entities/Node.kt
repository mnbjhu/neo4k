package uk.gibby.neo4k.returns.graph.entities

import org.neo4j.driver.internal.value.NodeValue
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.core.Searchable
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.open.OpenPath2
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.NodeReference
import uk.gibby.neo4k.returns.util.ReturnScope
import kotlin.reflect.KFunction
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

abstract class Node<T>: Entity<T>(){
    override fun parse(value: Any?): T {
        val nodeData = (value as NodeValue)
        val map = this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .associate { it.name to nodeData[it.name].asObject() }
        return ReturnScope(map).decode()
    }
    companion object{
        inline infix fun <reified A: Node<*>, reified B: DirectionalRelationship<A, C, *>, C: Node<*>>A.`--`(relation: RelationParamMap<B>) =
            OpenPath2(NodeReference(this), relation)

        inline infix fun <reified A: Node<*>, reified B: DirectionalRelationship<A, C, *>, C: Node<*>>A.`--`(relation: KFunction<B>): OpenPath2<A, B, C> =
            this.`--`(relation{})
    }
}