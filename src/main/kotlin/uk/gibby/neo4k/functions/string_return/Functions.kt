package uk.gibby.neo4k.functions.string_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.PrimitiveReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

private val arrayType = ArrayReturn::class.createType(listOf(KTypeProjection(KVariance.INVARIANT, String::class.createType()), KTypeProjection(KVariance.INVARIANT, StringReturn::class.createType())))


fun left(original: StringReturn, length: LongReturn) = ReturnValue.createReference(::StringReturn, "left(${original.getString()}, ${length.getString()})")
fun left(original: StringReturn, length: Long) = ReturnValue.createReference(::StringReturn, "left(${original.getString()},$length)")
fun left(original: String, length: LongReturn) = ReturnValue.createReference(::StringReturn, "left('$original',${length.getString()})")
fun left(original: String, length: Long) = ReturnValue.createReference(::StringReturn, "left('$original',$length)")

fun right(original: StringReturn, length: LongReturn) = ReturnValue.createReference(::StringReturn, "right(${original.getString()}, ${length.getString()})")
fun right(original: StringReturn, length: Long) = ReturnValue.createReference(::StringReturn, "right(${original.getString()},$length)")
fun right(original: String, length: LongReturn) = ReturnValue.createReference(::StringReturn, "right('$original',${length.getString()})")
fun right(original: String, length: Long) = ReturnValue.createReference(::StringReturn, "right('$original',$length)")

fun trim(original: StringReturn) = ReturnValue.createReference(::StringReturn, "trim(${original.getString()})")
fun trim(original: String) = ReturnValue.createReference(::StringReturn, "trim('$original')")

fun lTrim(original: StringReturn) = ReturnValue.createReference(::StringReturn, "lTrim(${original.getString()})")
fun lTrim(original: String) = ReturnValue.createReference(::StringReturn, "lTrim('$original')")

fun rTrim(original: StringReturn) = ReturnValue.createReference(::StringReturn, "rTrim(${original.getString()})")
fun rTrim(original: String) = ReturnValue.createReference(::StringReturn, "rTrim('$original')")

fun reverse(original: StringReturn) = ReturnValue.createReference(::StringReturn, "reverse(${original.getString()})")
fun reverse(original: String) = ReturnValue.createReference(::StringReturn, "reverse('$original')")

fun toLower(original: StringReturn) = ReturnValue.createReference(::StringReturn, "toLower(${original.getString()})")
fun toLower(original: String) = ReturnValue.createReference(::StringReturn, "toLower('$original')")

fun toUpper(original: StringReturn) = ReturnValue.createReference(::StringReturn, "toUpper(${original.getString()})")
fun toUpper(original: String) = ReturnValue.createReference(::StringReturn, "toUpper('$original')")

fun replace(original: StringReturn, search: StringReturn, replace: String) = ReturnValue.createReference(::StringReturn, "replace(${original.getString()},${search.getString()},'${replace}')")
fun replace(original: StringReturn, search: String, replace: String) = ReturnValue.createReference(::StringReturn, "replace(${original.getString()},'$search','${replace}')")
fun replace(original: String, search: StringReturn, replace: String) = ReturnValue.createReference(::StringReturn, "replace('$original',${search.getString()},'${replace}')")
fun replace(original: String, search: String, replace: String) = ReturnValue.createReference(::StringReturn, "replace('$original','$search','${replace}')")
fun replace(original: StringReturn, search: StringReturn, replace: StringReturn) = ReturnValue.createReference(::StringReturn, "replace(${original.getString()},${search.getString()},${replace.getString()})")
fun replace(original: StringReturn, search: String, replace: StringReturn) = ReturnValue.createReference(::StringReturn, "replace(${original.getString()},'$search',${replace.getString()})")
fun replace(original: String, search: StringReturn, replace: StringReturn) = ReturnValue.createReference(::StringReturn, "replace('$original',${search.getString()},${replace.getString()})")
fun replace(original: String, search: String, replace: StringReturn) = ReturnValue.createReference(::StringReturn, "replace('$original','$search',${replace.getString()})")

fun substring(original: StringReturn, start: LongReturn, length: Long? = null) = ReturnValue.createReference(::StringReturn, "substring(${original.getString()},${start.getString()}${if(length == null)"" else ",$length"})")
fun substring(original: StringReturn, start: Long, length: Long? = null) = ReturnValue.createReference(::StringReturn, "substring(${original.getString()},$start${if(length == null)"" else ",$length"})")
fun substring(original: String, start: LongReturn, length: Long? = null) = ReturnValue.createReference(::StringReturn, "substring('$original',${start.getString()}${if(length == null)"" else ",$length"})")
fun substring(original: String, start: Long, length: Long? = null) = ReturnValue.createReference(::StringReturn, "substring('$original',$start${if(length == null)"" else ",$length"})")
fun substring(original: StringReturn, start: LongReturn, length: LongReturn? = null) = ReturnValue.createReference(::StringReturn, "substring(${original.getString()},${start.getString()}${if(length == null)"" else ",${length.getString()}"})")
fun substring(original: StringReturn, start: Long, length: LongReturn? = null) = ReturnValue.createReference(::StringReturn, "substring(${original.getString()},$start${if(length == null)"" else ",${length.getString()}"})")
fun substring(original: String, start: LongReturn, length: LongReturn? = null) = ReturnValue.createReference(::StringReturn, "substring('$original',${start.getString()}${if(length == null)"" else ",${length.getString()}"})")
fun substring(original: String, start: Long, length: LongReturn? = null) = ReturnValue.createReference(::StringReturn, "substring('$original',$start${if(length == null)"" else ",${length.getString()}"})")

fun split(original: StringReturn, delimiter: StringReturn) =
    ReturnValue.createReference(arrayType, "split(${original.getString()}, ${delimiter.getString()})") as ArrayReturn<String, StringReturn>
fun split(original: String, delimiter: StringReturn) =
    ReturnValue.createReference(arrayType, "split('${original}', ${delimiter.getString()})") as ArrayReturn<String, StringReturn>
fun split(original: StringReturn, delimiter: String) =
    ReturnValue.createReference(arrayType, "split(${original.getString()}, '${delimiter}')") as ArrayReturn<String, StringReturn>
fun split(original: String, delimiter: String) =
    ReturnValue.createReference(arrayType, "split('${original}', '${delimiter}')") as ArrayReturn<String, StringReturn>

fun toString(original: PrimitiveReturn<*>) = ReturnValue.createReference(::StringReturn, "toString(${original.getString()})")
