package uk.gibby.neo4k.functions.conditions.primitive.long_return

import uk.gibby.neo4k.returns.ReturnValue.Companion.createReference
import uk.gibby.neo4k.returns.primitives.LongReturn

operator fun LongReturn.plus(other: LongReturn) = createReference(::LongReturn, "(${getString()} + ${other.getString()})")
operator fun LongReturn.plus(other: Long) = createReference(::LongReturn, "(${getString()} + $other)")
operator fun Long.plus(other: LongReturn) = createReference(::LongReturn, "($this + ${other.getString()})")

operator fun LongReturn.minus(other: LongReturn) = createReference(::LongReturn, "(${getString()} - ${other.getString()})")
operator fun LongReturn.minus(other: Long) = createReference(::LongReturn, "(${getString()} - $other)")
operator fun Long.minus(other: LongReturn) = createReference(::LongReturn, "($this - ${other.getString()})")

operator fun LongReturn.div(other: LongReturn) = createReference(::LongReturn, "(${getString()} / ${other.getString()})")
operator fun LongReturn.div(other: Long) = createReference(::LongReturn, "(${getString()} / $other)")
operator fun Long.div(other: LongReturn) = createReference(::LongReturn, "($this / ${other.getString()})")

operator fun LongReturn.times(other: LongReturn) = createReference(::LongReturn, "(${getString()} * ${other.getString()})")
operator fun LongReturn.times(other: Long) = createReference(::LongReturn, "(${getString()} * $other)")
operator fun Long.times(other: LongReturn) = createReference(::LongReturn, "($this * ${other.getString()})")

operator fun LongReturn.rem(other: LongReturn) = createReference(::LongReturn, "(${getString()} % ${other.getString()})")
operator fun LongReturn.rem(other: Long) = createReference(::LongReturn, "(${getString()} % $other)")
operator fun Long.rem(other: LongReturn) = createReference(::LongReturn, "($this % ${other.getString()})")

fun LongReturn.pow(other: LongReturn) = createReference(::LongReturn, "(${getString()} ^ ${other.getString()})")
fun LongReturn.pow(other: Long) = createReference(::LongReturn, "(${getString()} ^ $other)")
fun Long.pow(other: LongReturn) = createReference(::LongReturn, "($this ^ ${other.getString()})")