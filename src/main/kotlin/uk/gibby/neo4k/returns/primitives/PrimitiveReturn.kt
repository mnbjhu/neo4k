package uk.gibby.neo4k.returns.primitives

import uk.gibby.neo4k.returns.DataType

/**
 * Double return
 *
 * Represents a Redis String and can be returned from graph queries
 *
 */
sealed class PrimitiveReturn<T>(private val value: T?): DataType<T>(){
    protected open fun getPrimitiveString(from: T) = "$from"
    override fun getStructuredString() = when(value){
        null -> throw Exception("return_types.PrimitiveReturn cannot getStructuredString with out value set")
        else -> getPrimitiveString(value)
    }
}
