import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Limit.Companion.limit
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.OrderBy.Companion.orderByDesc
import uk.gibby.neo4k.clauses.Where.Companion.where
import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.core.Graph
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.functions.conditions.primitive.boolean_return.and
import uk.gibby.neo4k.functions.conditions.primitive.boolean_return.not
import uk.gibby.neo4k.functions.conditions.primitive.double_return.avg
import uk.gibby.neo4k.functions.conditions.primitive.eq
import uk.gibby.neo4k.functions.conditions.primitive.exists
import uk.gibby.neo4k.functions.conditions.primitive.long_return.count
import uk.gibby.neo4k.functions.conditions.primitive.long_return.greaterThan
import uk.gibby.neo4k.functions.conditions.primitive.string_return.contains
import uk.gibby.neo4k.functions.conditions.primitive.string_return.plus
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.paths.`←-o`
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.graph.entities.UnitDirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.many
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.StringReturn

class MoviesTest {
    val graph = Graph("neo4j", "bolt://localhost", 6379)
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
            actor.name + " acted in " + movie.title+ " with Hugh Laurie"
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
}

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
