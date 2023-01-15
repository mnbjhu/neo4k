package uk.gibby.neo4k.returns.multiple

import uk.gibby.neo4k.returns.ReturnValue

sealed interface MultipleReturn<T: MultipleReturn<T>>{
    fun getList(): List<ReturnValue<*>>
    fun parseList(values: List<ReturnValue<*>>): T
}