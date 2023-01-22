import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Limit.Companion.limit
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.OrderBy.Companion.orderByDesc
import uk.gibby.neo4k.clauses.Where.Companion.where
import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.functions.double_return.avg
import uk.gibby.neo4k.functions.long_return.count
import uk.gibby.neo4k.functions.long_return.greaterThan
import uk.gibby.neo4k.functions.string_return.contains
import uk.gibby.neo4k.functions.string_return.plus
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.paths.`←-o`
import uk.gibby.neo4k.queries.build
import uk.gibby.neo4k.queries.query
import uk.gibby.neo4k.queries.with
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.graph.entities.UnitDirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.multiple.many
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.StringReturn

class MoviesTest {
    val graph = Graph(
        name = "neo4j",
        host = "localhost",
        username = "neo4j",
        password = "myPassword123"
    )
    @Test
    fun `Basic Test #1`(){
        graph.query {
            val (actor, _, movie) = match(::Actor `o-→` ::ActedIn `o-→` ::Movie)
            limit(2500)
            actor.name + " acted in " + movie.title
        }.forEach { println(it) }
    }
    @Test
    fun `Basic Test #2`(){
        graph.query {
            val (actor, _, movie) = match(::Actor `o-→` ::ActedIn `o-→` ::Movie `←-o` ::ActedIn `←-o` ::Actor{it[name]="Hugh Laurie"})
            limit(2500)
            actor.name + " acted in " + movie.title + " with Hugh Laurie"
        }.forEach { println(it) }
    }


    fun `Basic Test #3`(){
        graph.query {
            val person = match(::Person)
            person.born
        }.forEach { println(it) }
    }

    @Test
    fun `Basic Test #4`(){
        graph.query {
            val (a, actedIn, m) = match(::Actor `o-→` ::ActedIn `o-→` ::Movie)
            val (actor, roleCount, movie) = using(a, count(actedIn), m)
            where(roleCount greaterThan 2)
            many(actor.name, movie)
        }.forEach { println(it) }
    }
    @Test
    fun `To List`(){
        graph.query {
            val (movie, userRating) = match(::Movie `←-o` ::Rated `←-o` ::User)
            val (title, averageRating, numberOfRatings) =
                using(movie.title, avg(userRating.rating), count(userRating))
            where(numberOfRatings greaterThan 100)
            orderByDesc(averageRating)
            limit(25)
            many(title, averageRating)
        }.forEach { println(it) }
    }
    @Test
    fun testNewQuery(){
        val myQuery = query {
            val (movie, userRating) = match(::Movie `←-o` ::Rated `←-o` ::User)
            many(movie.title, avg(userRating.rating), count(userRating))
        }.with { (title, averageRating, numberOfRatings) ->
            where(numberOfRatings greaterThan 100)
            orderByDesc(averageRating)
            limit(25)
            many(title, averageRating)
        }.build()
        graph.myQuery().forEach { println(it) }
    }

    @Test
    fun testNewQueryWithTwoParams(){
        val findSharedMovies = query(::StringReturn, ::StringReturn){ first, _ ->
            val actor = match(::Actor)
            where(actor.name contains first)
            actor
        }.with{ actor, (_, second) ->
            val (_, _, movie, _, actor2) = match(actor `o-→` ::ActedIn `o-→` ::Movie `←-o` ::ActedIn `←-o` ::Actor)
            where(actor2.name contains second)
            movie
        }.with { movie ->
            val (user, review) = match(::User `o-→` ::Rated `o-→` movie)
            many(movie.title, count(user), avg(review.rating))
        }.with { (title, numReviews, avgRating) ->
            where(numReviews greaterThan 20)
            orderByDesc(avgRating)
            limit(10)
            title
        }.build()
        graph.findSharedMovies("Ryder", "Reeves").forEach { println(it) }
    }
    @Test
    fun readme(){
        graph.findBestRatedMovies()

    }
}
val findBestRatedMovies = query {
    val (movie, review) = match(::Movie `←-o` ::Rated `←-o` ::User)
    many(movie.title, avg(review.rating), count(review))
}.with { (title, averageRating, numberOfReviews) ->
    where(numberOfReviews greaterThan 100)
    orderByDesc(averageRating)
    limit(25)
    many(title, averageRating)
}.build()



class Movie(
    val countries: ArrayReturn<String, StringReturn>,
    val imdbId: LongReturn,
    val imdbRating: DoubleReturn,
    val imdbVotes: LongReturn,
    val languages: ArrayReturn<String, StringReturn>,
    val movieId: StringReturn,
    val plot: StringReturn,
    val poster: StringReturn,
    val released: StringReturn,
    val runtime: LongReturn,
    val title: StringReturn,
    val tmdbId: LongReturn,
    val url: StringReturn,
    val year: LongReturn,
): UnitNode()

open class Person(
    val born: StringReturn,
    val died: StringReturn,
    val imdbId:	LongReturn,
    val name: StringReturn,
    val poster: StringReturn,
    val tmdbId: LongReturn,
    val url: StringReturn
): UnitNode()

class Director(
    born: StringReturn,
    died: StringReturn,
    imdbId:	LongReturn,
    name: StringReturn,
    poster: StringReturn,
    tmdbId: LongReturn,
    url: StringReturn
): Person(born, died, imdbId, name, poster, tmdbId, url)

class Actor(
    born: StringReturn,
    died: StringReturn,
    imdbId:	LongReturn,
    name: StringReturn,
    poster: StringReturn,
    tmdbId: LongReturn,
    url: StringReturn
): Person(born, died, imdbId, name, poster, tmdbId, url)

class ActedIn(val role: StringReturn): UnitDirectionalRelationship<Actor, Movie>()

class Directed(val role: StringReturn): UnitDirectionalRelationship<Director, Movie>()
data class User(val name: StringReturn, val userId: LongReturn): UnitNode()
data class Genre(val name: StringReturn): UnitNode()
data class Rated(val rating: DoubleReturn, val timestamp: LongReturn): UnitDirectionalRelationship<User, Movie>()
class InGenre: UnitDirectionalRelationship<Movie, Genre>()
