package uk.gibby.neo4k.returns.generic

import org.neo4j.driver.internal.value.ListValue
import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.util.Box
import java.lang.ClassCastException
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

/**
 * Array return
 *
 * Represents a Redis Array<[T]> and can be returned from graph queries
 *
 * @sample [e2e.types.Array.createLiteral]
 * @sample [e2e.types.Array.createAttribute]
 * @sample [e2e.types.Array.matchAttribute]
 */
class ArrayReturn<T, U: ReturnValue<T>>(private val values: Box<List<U>>, internal val inner: KType): DataType<List<T>>() {
    override fun getStructuredString(): String {
        return when(values){
            is Box.WithoutValue -> throw Exception("return_types.ArrayReturn cannot getStructuredString with out values set")
            is Box.WithValue -> values.value.joinToString(prefix = "[", postfix = "]") { it.getString() }
            else -> throw java.lang.Exception("IDE BUG")
        }
    }
    override fun parse(value: Any?): List<T> {
        val dummy = createDummy(inner) as U
        val list = try { value as List<*> } catch (_: ClassCastException){ (value as ListValue).asList() }
        return list.map { dummy.parse(it) }
    }

    override fun encode(value: List<T>): ArrayReturn<T, U> {
        val dummy = createDummy(inner) as U
        return ArrayReturn(Box.WithValue(value.map { dummy.encode(it) as U }), inner)
    }
    operator fun plus(other: ArrayReturn<T, U>): ArrayReturn<T, U>{
        return createReference(inner, "(${this.getString()} + ${other.getString()})") as ArrayReturn<T, U>
    }

    operator fun get(index: Long) = createReference(inner, "${getString()}[$index]")
    operator fun get(index: LongReturn) = createReference(inner, "${getString()}[${index.getString()}]")

    operator fun plus(other: List<T>) = createReference(inner, "(${this.getString()} + ${encode(other).getString()})")
    fun all(predicate: (U) -> BooleanReturn): BooleanReturn {
        val newElementRef = NameCounter.next()
        val element = createReference(inner, newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "all($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun any(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = createReference(inner, newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "any($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun none(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = createReference(inner, newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "none($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun single(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = createReference(inner, newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "single($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun <V, R: ReturnValue<V>>map(transform: (U) -> R): ArrayReturn<V, R>{
        val newElementRef = NameCounter.next()
        val element = createReference(inner, newElementRef) as U
        val newElement = transform(element)
        val newElementReturnType = newElement::class.supertypes.last { it.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }.arguments[0]
        val newElementType: KType = if(newElement is ArrayReturn<*, *>){
            ArrayReturn::class.createType(listOf(newElementReturnType.type!!.arguments[0], KTypeProjection(KVariance.INVARIANT, newElement.inner)))
        } else{
            newElement::class.createType()
        }


        return createReference(
            ArrayReturn::class.createType(listOf(newElementReturnType, KTypeProjection(KVariance.INVARIANT, newElementType))),
            "[$newElementRef IN ${this@ArrayReturn.getString()} | ${newElement.getString()}]"
        ) as ArrayReturn<V, R>
    }
}