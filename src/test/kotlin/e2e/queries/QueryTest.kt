package e2e.queries
import uk.gibby.neo4k.core.invoke
import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.queries.Query0.Companion.query
import util.GraphTest

class QueryTest: GraphTest() {
    @BeforeEach
    fun createExample(){
        graph.query {
            val (_, _, movie) = create(::Actor{ it[name] = "Mark Hamill" } `o-→` ::ActedIn{ it[role] = "Luke Skywalker" } `o-→`
                    ::Movie{ it[title] = "Star Wars: Episode V - The Empire Strikes Back"; it[releaseYear] = 1980 })
            create(::Actor{ it[name] = "Carrie Fisher" } `o-→` ::ActedIn{ it[role] = "Princess Leia" } `o-→` movie)
            create(::Actor{ it[name] = "Harrison Ford" } `o-→` ::ActedIn{ it[role] = "Han Solo" } `o-→` movie)
        }
    }
    @Test
    fun testQueryWithoutParams(){
        val myQuery = query {
            val movie = match(::Movie)
            movie.title
        }
        val movies = graph.myQuery()
        movies `should be equal to` listOf("Star Wars: Episode V - The Empire Strikes Back")
    }

    /*
    @Test
    fun testQueryWithOneParam(){
        val findMoviesContainingText = query(::StringReturn) { searchText ->
            val movie = match(::Movie)
            where(movie.title contains searchText)
            movie.title
        }
        graph.findMoviesContainingText("Star Wars").size `should be equal to` 1
        graph.findMoviesContainingText("Lord Of The Rings").size `should be equal to` 0
    } */
}