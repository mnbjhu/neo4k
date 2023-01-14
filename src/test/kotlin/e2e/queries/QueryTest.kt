package e2e.queries
import uk.gibby.neo4k.core.invoke
import e2e.schemas.ActedIn
import e2e.schemas.Actor
import e2e.schemas.Movie
import e2e.types.struct.VectorExample
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.Where.Companion.where
import uk.gibby.neo4k.functions.boolean_return.and
import uk.gibby.neo4k.functions.eq
import uk.gibby.neo4k.functions.long_return.greaterThan
import uk.gibby.neo4k.functions.long_return.lessThan
import uk.gibby.neo4k.functions.string_return.contains
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.queries.build
import uk.gibby.neo4k.queries.query
import uk.gibby.neo4k.queries.query1
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
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
        }.build()
        val movies = graph.myQuery()
        movies `should be equal to` listOf("Star Wars: Episode V - The Empire Strikes Back")
    }

    @Test
    fun testQueryWithOneParam(){
        val findMoviesContainingText = query1 { searchText: StringReturn ->
            val movie = match(::Movie)
            where(movie.title contains searchText)
            movie.title
        }.build()
        graph.findMoviesContainingText("Star Wars").size `should be equal to` 1
        graph.findMoviesContainingText("Lord Of The Rings").size `should be equal to` 0
    }

    @Test
    fun queryWithTwoParams(){
        val myQuery = query(::StringReturn, VectorExample::Vector2Return) { searchText, yearRange ->
            val movie = match(::Movie)
            where(
                (movie.title contains searchText) and
                (movie.releaseYear lessThan yearRange.y) and
                (movie.releaseYear greaterThan yearRange.x)
            )
            movie.title
        }.build()
        graph.myQuery("Star Wars", VectorExample.Vector2(1900, 2000)).size `should be equal to` 1
        graph.myQuery("Star Wars", VectorExample.Vector2(2000, 2100)).size `should be equal to` 0
        graph.myQuery("Lord Of The Rings", VectorExample.Vector2(1900, 2000)).size `should be equal to` 0
        graph.myQuery("Lord Of The Rings", VectorExample.Vector2(2000, 2100)).size `should be equal to` 0
    }

    @Test
    fun bulkTest(){
        val myQuery = query(::LongReturn) { count ->
            val movie = create(::Movie{it[title] = "Test Movie"; it[releaseYear] = count})
            movie
        }.build()
        for (i in 1.. 1000L){
            graph.myQuery(i)
        }
    }

    @Test
    fun threeParamQuery(){
        val myQuery = query(::StringReturn, ::LongReturn, ::LongReturn) { text, bottom, top ->
            EmptyReturnInstance
        }
    }
}