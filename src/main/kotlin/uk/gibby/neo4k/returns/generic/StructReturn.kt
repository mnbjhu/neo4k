package uk.gibby.neo4k.returns.generic

import org.neo4j.driver.internal.value.ListValue
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import java.lang.ClassCastException
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

abstract class StructReturn<T>: DataType<T>(){
    override fun getStructuredString(): String {
        return this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .joinToString(prefix = "[", postfix = "]") {
                val value = it.call(this) as ReturnValue<*>
                value.getString()
            }
    }
    abstract override fun encode(value: T): StructReturn<T>
    override fun parse(value: Any?): T {
        val values = try {
            value as List<*>} catch (_: ClassCastException){(value as ListValue).asList()}.iterator()
        val map = this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .associate { it.name to values.next() }
        return ReturnScope(map).decode()
    }
    abstract fun ReturnScope.decode(): T
    operator fun <T, U: ReturnValue<T>>U.get(value: T): U{
        return encode(value) as U
    }
}


