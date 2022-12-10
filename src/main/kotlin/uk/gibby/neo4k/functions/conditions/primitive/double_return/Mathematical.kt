package uk.gibby.neo4k.functions.conditions.primitive.double_return

import uk.gibby.neo4k.returns.ReturnValue.Companion.createReference
import uk.gibby.neo4k.returns.primitives.DoubleReturn

operator fun DoubleReturn.plus(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} + ${other.getString()})")
operator fun DoubleReturn.plus(other: Double) = createReference(::DoubleReturn, "(${getString()} + $other)")
operator fun Double.plus(other: DoubleReturn) = createReference(::DoubleReturn, "($this + ${other.getString()})")

operator fun DoubleReturn.minus(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} - ${other.getString()})")
operator fun DoubleReturn.minus(other: Double) = createReference(::DoubleReturn, "(${getString()} - $other)")
operator fun Double.minus(other: DoubleReturn) = createReference(::DoubleReturn, "($this - ${other.getString()})")

operator fun DoubleReturn.div(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} / ${other.getString()})")
operator fun DoubleReturn.div(other: Double) = createReference(::DoubleReturn, "(${getString()} / $other)")
operator fun Double.div(other: DoubleReturn) = createReference(::DoubleReturn, "($this / ${other.getString()})")

operator fun DoubleReturn.times(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} * ${other.getString()})")
operator fun DoubleReturn.times(other: Double) = createReference(::DoubleReturn, "(${getString()} * $other)")
operator fun Double.times(other: DoubleReturn) = createReference(::DoubleReturn, "($this * ${other.getString()})")

operator fun DoubleReturn.rem(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} % ${other.getString()})")
operator fun DoubleReturn.rem(other: Double) = createReference(::DoubleReturn, "(${getString()} % $other)")
operator fun Double.rem(other: DoubleReturn) = createReference(::DoubleReturn, "($this % ${other.getString()})")

fun DoubleReturn.pow(other: DoubleReturn) = createReference(::DoubleReturn, "(${getString()} ^ ${other.getString()})")
fun DoubleReturn.pow(other: Double) = createReference(::DoubleReturn, "(${getString()} ^ $other)")
fun Double.pow(other: DoubleReturn) = createReference(::DoubleReturn, "($this ^ ${other.getString()})")