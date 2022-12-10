package e2e.schemas

import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.graph.entities.UnitDirectionalRelationship
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.StringReturn

class Actor(val name: StringReturn): UnitNode()
class ActedIn(val role: StringReturn): UnitDirectionalRelationship<Actor, Movie>()
class Movie(
    val title: StringReturn,
    val releaseYear: LongReturn
): UnitNode()