package uk.gibby.neo4k.functions.conditions.primitive.array_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.primitives.BooleanReturn

infix fun <T, U: ReturnValue<T>>ArrayReturn<T, U>.contains(element: U) = ReturnValue.createReference(::BooleanReturn, "(${element.getString()} IN ${getString()})")
infix fun <T, U: ReturnValue<T>>ArrayReturn<T, U>.contains(element: T) = ReturnValue.createReference(::BooleanReturn, "($element IN ${getString()})")