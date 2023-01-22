package uk.gibby.neo4k.functions.double_return

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.DoubleReturn


fun rand() = ReturnValue.createReference(::DoubleReturn, "rand()")
