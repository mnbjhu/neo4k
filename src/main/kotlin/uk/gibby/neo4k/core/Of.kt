package uk.gibby.neo4k.core

import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.ReturnValue.Companion.createDummy
import uk.gibby.neo4k.returns.ReturnValue.Companion.createInstance
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.generic.Nullable
import uk.gibby.neo4k.returns.util.Box
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

infix fun <T, U: ReturnValue<T>>KFunction<U>.of(value: T): U = createInstance(this, value)

class TypeProducer<T, U: ReturnValue<T>>(val inner: U)

fun <T, U: NotNull<T>>nullable(type: KFunction<U>) = TypeProducer(
    Nullable(Box.WithoutValue, createDummy(type))
)
inline fun <reified T, U: NotNull<T>>nullable(type: TypeProducer<T, U>) = TypeProducer(
    Nullable(Box.WithoutValue, type.inner)
)

fun <T, U: NotNull<T>>array(type: KFunction<U>) = TypeProducer(
    ArrayReturn(Box.WithoutValue, createDummy(type.returnType) as U)
)
inline fun <reified T, U: ReturnValue<T>>array(type: TypeProducer<T, U>) = TypeProducer(
    ArrayReturn(Box.WithoutValue, type.inner)
)

infix fun <T, U: ReturnValue<T>>TypeProducer<T, U>.of(value: T): U = inner.encode(value) as U