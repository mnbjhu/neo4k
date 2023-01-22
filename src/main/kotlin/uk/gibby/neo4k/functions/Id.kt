package uk.gibby.neo4k.functions

import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.graph.entities.Entity
import uk.gibby.neo4k.returns.primitives.LongReturn

fun id(of: Entity<*>) = ReturnValue.createReference(::LongReturn, "ID(${of.getString()})")