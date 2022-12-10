package uk.gibby.neo4k.core

import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.ReturnValue.Companion.createInstance
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.generic.Nullable
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

infix fun <T, U: ReturnValue<T>>KFunction<U>.of(value: T): U = createInstance(this, value)

class TypeProducer<T, U: ReturnValue<T>>(val type: KType)

inline fun <reified T, U: NotNull<T>>nullable(type: KFunction<U>) = TypeProducer<T?, Nullable<T, U>>(
    Nullable::class.createType(listOf(
    KTypeProjection(KVariance.INVARIANT, T::class.createType()),
    KTypeProjection(KVariance.INVARIANT, type.returnType),
)))

inline fun <reified T, U: NotNull<T>>nullable(type: TypeProducer<T, U>) = TypeProducer<T?, Nullable<T, U>>(
    Nullable::class.createType(listOf(
        type.type.arguments[0],
        KTypeProjection(KVariance.INVARIANT, type.type),
    )))

inline fun <reified T, U: NotNull<T>>array(type: KFunction<U>) = TypeProducer<List<T>, ArrayReturn<T, U>>(
    ArrayReturn::class.createType(listOf(
        KTypeProjection(KVariance.INVARIANT, T::class.createType()),
        KTypeProjection(KVariance.INVARIANT, type.returnType),
    )))
inline fun <reified T, U: ReturnValue<T>>array(type: TypeProducer<T, U>) = TypeProducer<List<T>, ArrayReturn<T, U>>(
    ArrayReturn::class.createType(listOf(
        KTypeProjection(KVariance.INVARIANT, T::class.createType()),
        KTypeProjection(KVariance.INVARIANT, type.type),
    )))

infix fun <T, U: ReturnValue<T>>TypeProducer<T, U>.of(value: T) = createInstance<T, U>(type, value)