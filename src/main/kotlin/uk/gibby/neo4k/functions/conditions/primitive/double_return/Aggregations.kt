package uk.gibby.neo4k.functions.conditions.primitive.double_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.DoubleReturn

fun avg(number: DoubleReturn) = ReturnValue.createReference(::DoubleReturn, "avg(${number.getString()})")

fun min(number: DoubleReturn) = ReturnValue.createReference(::DoubleReturn, "min(${number.getString()})")

fun max(number: DoubleReturn) = ReturnValue.createReference(::DoubleReturn, "max(${number.getString()})")

fun sum(number: DoubleReturn) = ReturnValue.createReference(::DoubleReturn, "sum(${number.getString()})")
