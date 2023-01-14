package e2e.clauses

import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.Where.Companion.where
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.functions.string_return.contains
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class Where: GraphTest() {
    @BeforeEach
    fun setupTestData(){
        graph.query {
            val (_, _, movie) = create(::Actor{ it[name] = "Mark Hamill" } `o-→` ::ActedIn{ it[role] = "Luke Skywalker" } `o-→`
                    ::Movie{ it[title] = "Star Wars: Episode V - The Empire Strikes Back"; it[releaseYear] = 1980 })
            create(::Actor{ it[name] = "Carrie Fisher" } `o-→` ::ActedIn{ it[role] = "Princess Leia" } `o-→` movie)
            create(::Actor{ it[name] = "Harrison Ford" } `o-→` ::ActedIn{ it[role] = "Han Solo" } `o-→` movie)
        }
    }
    @Test
    fun basicTest(){
        graph.query {
            val actor = match(::Actor)
            where(actor.name contains "o")
            actor.name
        } `should be equal to` listOf("Harrison Ford")
    }
}