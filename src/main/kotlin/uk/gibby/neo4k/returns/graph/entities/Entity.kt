package uk.gibby.neo4k.returns.graph.entities

import org.neo4j.driver.types.Entity
import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

sealed class Entity<T>: NotNull<T>(){
    override fun getStructuredString(): String {
        throw Exception("Only references to entities should be created")
    }

    override fun encode(value: T): ReturnValue<T> {
        throw Exception("Only references to entities should be created")
    }

    abstract fun ReturnScope.decode(): T
}