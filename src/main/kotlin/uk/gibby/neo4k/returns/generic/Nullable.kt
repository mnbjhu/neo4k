package uk.gibby.neo4k.returns.generic

import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.KSerializer
import org.neo4j.driver.internal.value.NullValue
import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.Box
import uk.gibby.neo4k.returns.util.ReturnValueType
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType
import kotlin.reflect.full.withNullability

/**
 * Nullable return
 *
 * Represents a Redis nullable value and can be returned from graph queries
 *
 * @sample [e2e.types.primitive.String.createNullLiteral]
 * @sample [e2e.types.Array.createNullLiteral]
 */
class Nullable<T: Any, U: NotNull<T>>(private val value: Box<U>, private val dummy: U): ReturnValue<T?>() {
    override val serializer: KSerializer<T?> = dummy.serializer.nullable

    override fun getStructuredString() = when(value){
        is Box.WithoutValue -> "NULL"
        is Box.WithValue -> value.value.getString()
    }
    override fun parse(value: Any?): T? {

        return if (value != null && value !is NullValue) dummy.parse(value)
        else null
    }
    override fun createReference(newRef: String): Nullable<T, U>{
        return Nullable(Box.WithoutValue, dummy).also { type = ReturnValueType.Reference(newRef) }
    }

    override fun createDummy() = Nullable(Box.WithoutValue, dummy).apply { type = ReturnValueType.ParserOnly }

    override fun encode(value: T?): Nullable<T, U> {
        return if (value == null) Nullable(Box.WithoutValue, dummy)
        else Nullable(Box.WithValue(dummy.encode(value) as U), dummy)
    }
}