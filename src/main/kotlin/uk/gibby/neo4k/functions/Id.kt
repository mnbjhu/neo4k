package uk.gibby.neo4k.functions

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.graph.entities.Entity
import uk.gibby.neo4k.returns.primitives.LongReturn

fun elementId(of: Entity<*>) = ReturnValue.createReference(::LongReturn, "elementId(${of.getString()})")