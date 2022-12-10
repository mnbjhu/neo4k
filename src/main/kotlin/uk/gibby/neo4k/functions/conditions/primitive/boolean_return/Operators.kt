package uk.gibby.neo4k.functions.conditions.primitive.boolean_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.BooleanReturn

operator fun BooleanReturn.not() = ReturnValue.createReference(::BooleanReturn, "(NOT ${getString()})")
infix fun BooleanReturn.and(other: BooleanReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} AND ${other.getString()})")
infix fun BooleanReturn.or(other: BooleanReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} OR ${other.getString()})")
infix fun BooleanReturn.xor(other: BooleanReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} XOR ${other.getString()})")