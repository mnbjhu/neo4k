package uk.gibby.neo4k.returns.generic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.Referencable
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.util.Box
import uk.gibby.neo4k.returns.util.ReturnValueType
import java.lang.ClassCastException
import java.util.Arrays
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
class ArrayReturn<T, U: ReturnValue<T>>(private val values: Box<List<U>>, internal val inner: U): DataType<List<T>>() {
    override val serializer: KSerializer<List<T>>
        get() = ListSerializer(inner.serializer)

    override fun getStructuredString(): String {
        return when(values){
            is Box.WithoutValue -> throw Exception("return_types.ArrayReturn cannot getStructuredString with out values set")
            is Box.WithValue -> values.value.joinToString(prefix = "[", postfix = "]") { it.getString() }
            else -> throw java.lang.Exception("IDE BUG")
        }
    }

    override fun createReference(newRef: String): ArrayReturn<T, U>  = ArrayReturn(Box.WithoutValue, inner)
        .apply { type = ReturnValueType.Reference(newRef) }

    override fun createDummy() = ArrayReturn(Box.WithoutValue, inner).apply { type = ReturnValueType.ParserOnly("inner") }


    override fun encode(value: List<T>): ArrayReturn<T, U> {
        return ArrayReturn(Box.WithValue(value.map { inner.encode(it) as U }), inner)
    }
    operator fun plus(other: ArrayReturn<T, U>): ArrayReturn<T, U>{
        return createReference("(${this.getString()} + ${other.getString()})")
    }

    operator fun get(index: Long) = createReference("${getString()}[$index]")
    operator fun get(index: LongReturn) = createReference("${getString()}[${index.getString()}]")

    operator fun plus(other: List<T>) = createReference("(${this.getString()} + ${encode(other).getString()})")
    fun all(predicate: (U) -> BooleanReturn): BooleanReturn {
        val newElementRef = NameCounter.next()
        val element = inner.createReference(newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "all($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun any(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = inner.createReference(newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "any($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun none(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = inner.createReference(newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "none($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun single(predicate: (U) -> BooleanReturn): BooleanReturn{
        val newElementRef = NameCounter.next()
        val element = inner.createReference(newElementRef) as U
        val condition = predicate(element)
        return createReference(
            ::BooleanReturn,
            "single($newElementRef IN ${this@ArrayReturn.getString()} WHERE ${condition.getString()})"
        )
    }
    fun <V, R: ReturnValue<V>>map(transform: (U) -> R): ArrayReturn<V, R>{
        val newElementRef = NameCounter.next()
        val element = inner.createReference(newElementRef) as U
        val newElement = transform(element)
        return ArrayReturn(Box.WithoutValue, newElement.createDummy() as R)
            .apply { type = ReturnValueType.Reference("[$newElementRef IN ${this@ArrayReturn.getString()} | ${newElement.getString()}]") }
    }

}

fun <T, U: DataType<T>>toList(query: QueryScope.() -> U): ArrayReturn<T, U>
    {
        val scope = QueryScope()
        val result = scope.query()
        return ArrayReturn(Box.WithoutValue, result.createDummy() as U).apply { type = ReturnValueType.Reference("[${scope.getString().drop(6)} | ${result.getString()}]")}
    }