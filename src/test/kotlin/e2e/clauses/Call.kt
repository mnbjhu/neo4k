package e2e.clauses

import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.ForEach.Companion.forEach
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.array
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.queries.query
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.multiple.none
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
import util.GraphTest

class Call : GraphTest(){
    @Test
    fun forEachTest(){
        val myList = listOf("something1", "something2")
        graph.query { forEach(array(::StringReturn) of myList) {  name -> create(::Actor{ it[name] = name }) } }
        graph.query {
            val actors = match(::Actor)
            actors.name
        } `should contain same` myList
    }

    @Test
    fun callTest(){
        val myList = listOf("something1", "something2")
        graph.query { forEach(array(::StringReturn) of myList) {  name -> query{ create(::Actor{ it[name] = name }) }.call() } }
        graph.query {
            val actors = match(::Actor)
            actors.name
        } `should contain same` myList
    }
}