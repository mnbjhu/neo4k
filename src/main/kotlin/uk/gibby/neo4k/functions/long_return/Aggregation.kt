package uk.gibby.neo4k.functions.long_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.LongReturn

fun count(item: ReturnValue<*>) = ReturnValue.createReference(::LongReturn, "count(${item.getString()})")

fun avg(number: DoubleReturn) = ReturnValue.createReference(::DoubleReturn, "avg(${number.getString()})")

fun min(number: LongReturn) = ReturnValue.createReference(::LongReturn, "min(${number.getString()})")

fun max(number: LongReturn) = ReturnValue.createReference(::LongReturn, "max(${number.getString()})")