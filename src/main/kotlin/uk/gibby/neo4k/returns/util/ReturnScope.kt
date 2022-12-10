package uk.gibby.neo4k.returns.util

import uk.gibby.neo4k.returns.ReturnValue
import kotlin.reflect.KProperty

class ReturnScope(private val map: Map<String, *>) {
    fun <T, U: ReturnValue<T>>KProperty<U>.result(): T{
        return map[name] as T
    }
}