package e2e.clauses

import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldHaveSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class Match: GraphTest() {
    @BeforeEach
    fun setupTestData(){
        graph.query {
            val (_, _, movie) = create(::Actor{ it[name] = "Mark Hamill" } `o-→` ::ActedIn{ it[role] = "Luke Skywalker" } `o-→`
                ::Movie{ it[title] = "Star Wars: Episode V - The Empire Strikes Back"; it[releaseYear] = 1980 })
            create((::Actor{ it[name] = "Carrie Fisher" } `o-→` ::ActedIn{ it[role] = "Princess Leia" }) `o-→` movie)
            create(::Actor{ it[name] = "Harrison Ford" } `o-→` ::ActedIn{ it[role] = "Han Solo" } `o-→` movie)
        }
    }
    @Test
    fun matchOneNode(){
        // MATCH (obj10:Movie)
        graph.query {
            val movie = match(::Movie)
            movie.title
        } `should be equal to` listOf("Star Wars: Episode V - The Empire Strikes Back")
    }
    @Test
    fun matchOneNodeFiltered(){
        // MATCH (obj10:Actor{name:'Mark Hamill'})
        graph.query {
            val actor = match(::Actor{it[name] = "Mark Hamill"})
            actor.name
        } shouldHaveSize 1
    }
    @Test
    fun matchTwoNodesFiltered(){
        // MATCH (obj10:Actor{name:'Mark Hamill'})
        graph.query {
            val (actor, _) = match(
                ::Actor{it[name] = "Mark Hamill"},
                ::Movie{it[title] = "Star Wars: Episode V - The Empire Strikes Back"}
            )
            actor.name
        } shouldHaveSize 1
    }
    @Test
    fun matchTwoNodes(){
        // MATCH (obj10:Actor), (obj11:Movie)
        graph.query {
            val (actor, _) = match(::Actor, ::Movie)
            actor.name
        } shouldHaveSize 3
    }

    @Test
    fun matchPath(){
        graph.query {
            // MATCH (obj10:Actor)-[obj11:ActedIn]->(obj12:Movie)
            val (_, actedIn, _) = match(::Actor `o-→` ::ActedIn `o-→`  ::Movie)
            actedIn.role
        } shouldContainSame listOf(
            "Princess Leia",
            "Han Solo",
            "Luke Skywalker"
        )
    }
}