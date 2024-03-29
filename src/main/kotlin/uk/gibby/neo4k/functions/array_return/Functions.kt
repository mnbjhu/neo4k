package uk.gibby.neo4k.functions.array_return

import uk.gibby.neo4k.core.TypeProducer
import uk.gibby.neo4k.core.array
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

private val intArrayType = ArrayReturn::class.createType(listOf(
    KTypeProjection(KVariance.INVARIANT, Long::class.createType()), KTypeProjection(
        KVariance.INVARIANT, LongReturn::class.createType())
))

fun <U: ArrayReturn<*, *>>isEmpty(array: U) = ReturnValue.createReference(::BooleanReturn, "isEmpty(${array.getString()})")

fun <U: ArrayReturn<*, *>>reverse(original: U) = original.createReference("reverse(${original.getString()})") as U

fun range(start: LongReturn, end: LongReturn, step: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range(${start.getString()},${end.getString()},${step})") as ArrayReturn<Long, LongReturn>
fun range(start: LongReturn, end: Long, step: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range(${start.getString()},$end,$step)") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: LongReturn, step: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range($start,${end.getString()},$step)") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: Long, step: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(intArrayType, "range($start,$end,$step)") as ArrayReturn<Long, LongReturn>
fun range(start: LongReturn, end: LongReturn, step: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range(${start.getString()},${end.getString()},$step)") as ArrayReturn<Long, LongReturn>
fun range(start: LongReturn, end: Long, step: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range(${start.getString()},$end,$step)") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: LongReturn, step: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range($start,${end.getString()},$step)") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: Long, step: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(
    intArrayType, "range($start,$end,$step)") as ArrayReturn<Long, LongReturn>

fun range(start: LongReturn, end: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(intArrayType, "range(${start.getString()},${end.getString()})") as ArrayReturn<Long, LongReturn>
fun range(start: LongReturn, end: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(intArrayType, "range(${start.getString()},$end)") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: LongReturn): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(intArrayType, "range($start,${end.getString()})") as ArrayReturn<Long, LongReturn>
fun range(start: Long, end: Long): ArrayReturn<Long, LongReturn> = ReturnValue.createReference(intArrayType, "range($start,$end)") as ArrayReturn<Long, LongReturn>

fun <T, U: DataType<T>>collect(expression: U): ArrayReturn<T, U> = array(TypeProducer(expression.createDummy())).inner.createReference("collect(${expression.getString()})") as ArrayReturn<T, U>