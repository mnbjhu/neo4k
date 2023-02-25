package e2e.clauses

import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.ForEach.Companion.forEach
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.array
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.queries.query
import uk.gibby.neo4k.returns.primitives.StringReturn
import util.GraphTest

class Call : GraphTest(){
    @Test
    fun forEachTest(){
        val myList = listOf("something1", "something2")
        graph.query { forEach(array(::StringReturn) of myList) {  name -> create(::Actor{ it[this.name] = name }) } }
        graph.query {
            val actors = match(::Actor)
            actors.name
        } `should contain same` myList
    }

    @Test
    fun callTest(){
        val myList = listOf("something1", "something2", "something3")
        graph.query { forEach(array(::StringReturn) of myList) {  name -> create(::Actor{ it[this.name] = name }) } }
        graph.query { forEach(array(::StringReturn) of myList) {  name -> create(::Movie{ it[title] = name; it[releaseYear] = 1234}) } }
        graph.query {
            val actor = match(::Actor)
            query {
                val movie = match(::Movie)
                create(actor `o-→` ::ActedIn `o-→` movie)
                movie
            }.call()
        }
    }
}