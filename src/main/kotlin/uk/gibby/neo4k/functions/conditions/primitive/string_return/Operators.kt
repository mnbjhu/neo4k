package uk.gibby.neo4k.functions.conditions.primitive.string_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.StringReturn

operator fun StringReturn.plus(other: StringReturn) = ReturnValue.createReference(::StringReturn, "(${getString()} + ${other.getString()})")
operator fun StringReturn.plus(other: String) = ReturnValue.createReference(::StringReturn, "(${getString()} + '$other')")

infix fun StringReturn.contains(other: StringReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} CONTAINS ${other.getString()})")
infix fun StringReturn.contains(other: String) = ReturnValue.createReference(::BooleanReturn, "(${getString()} CONTAINS '${other}')")

infix fun StringReturn.startsWith(other: StringReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} STARTS WITH ${other.getString()})")
infix fun StringReturn.startsWith(other: String) = ReturnValue.createReference(::BooleanReturn, "(${getString()} STARTS WITH '${other})'")

infix fun StringReturn.endsWith(other: StringReturn) = ReturnValue.createReference(::BooleanReturn, "(${getString()} ENDS WITH ${other.getString()})")
infix fun StringReturn.endsWith(other: String) = ReturnValue.createReference(::BooleanReturn, "(${getString()} ENDS WITH '${other})'")

