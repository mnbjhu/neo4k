package uk.gibby.neo4k.returns.graph.entities

import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.generic.StructReturn
import uk.gibby.neo4k.returns.util.ReturnScope
import kotlin.reflect.full.createType

sealed class Entity<T>: NotNull<T>(){
    override fun getStructuredString(): String {
        throw Exception("Only references to entities should be created")
    }

    override fun encode(value: T): ReturnValue<T> {
        throw Exception("Only references to entities should be created")
    }

    override fun createReference(newRef: String): Entity<T> {
        return Companion.createReference(this::class.createType(), newRef) as Entity<T>
    }
    abstract fun ReturnScope.decode(): T
    override fun createDummy(): Entity<T> = createDummy(this::class.createType()) as Entity<T>
}