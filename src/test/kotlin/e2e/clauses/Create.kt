package e2e.clauses

import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class Create: GraphTest() {
    @Test
    fun createNode(){
        // CREATE (obj0:Movie{title:'Pulp Fiction', releaseYear:'1994'})
        graph.query { create(::Movie{ it[title] = "Pulp Fiction"; it[releaseYear] = 1994 }) }
    }
    @Test
    fun createPath(){
        // CREATE (obj0:Actor{name:'John Travolta'})-[obj1:ActedIn{role:'Vincent Vega'}]->(obj2:Movie{title:'Pulp Fiction', releaseYear:'1994'})
        graph.query {
            create(::Actor{it[name] = "John Travolta"} `o-→` ::ActedIn{it[role] = "Vincent Vega"} `o-→` ::Movie{ it[title] = "Pulp Fiction"; it[releaseYear] = 1994 })
        }
    }
    @Test
    fun createPathToReference(){
        // MATCH (obj0:Movie{title:'Pulp Fiction'})
        // CREATE (obj1:Actor{name:'John Travolta'})-[obj2:ActedIn{role:'Vincent Vega'}]->(obj0)
        graph.query {
            val pulpFiction = match(::Movie{ it[title] = "Pulp Fiction"})
            create(::Actor{it[name] = "John Travolta"} `o-→` ::ActedIn{it[role] = "Vincent Vega"} `o-→` pulpFiction)
        }
    }
    @Test
    fun createNodeWithRefParameter(){
        createPath()
        graph.query {
            val actor = match(::Actor)
            val movie = create(::Movie{ it[title] = actor.name; it[releaseYear] = 2022 })
            movie.title
        } `should be equal to` listOf("John Travolta")
    }
}