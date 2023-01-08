package e2e.serialization

import Movie
import Rated
import User
import e2e.schemas.UserNode

import e2e.types.struct.VectorExample
import kotlinx.serialization.json.Json
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Limit.Companion.limit
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.OrderBy.Companion.orderByDesc
import uk.gibby.neo4k.clauses.Where.Companion.where
import uk.gibby.neo4k.clauses.WithAs.Companion.using
import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.functions.conditions.primitive.double_return.avg
import uk.gibby.neo4k.functions.conditions.primitive.long_return.count
import uk.gibby.neo4k.functions.conditions.primitive.long_return.greaterThan
import uk.gibby.neo4k.paths.`←-o`
import uk.gibby.neo4k.queries.build
import uk.gibby.neo4k.queries.query
import uk.gibby.neo4k.returns.MultipleReturn2
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
import java.io.File
import kotlin.test.Ignore

class SerializationTest {
     @Test
     fun testSerializer(){
         val intArraySerializer = array(::LongReturn).inner.serializer
         val stringData = Json.encodeToString(intArraySerializer, listOf(1, 2, 3))
         stringData `should be equal to` "[1,2,3]"
         Json.decodeFromString(intArraySerializer, stringData) `should be equal to` listOf(1, 2, 3)
     }
    @Test
    fun testStructSerializer(){
        val parser = ReturnValue.createDummy(VectorExample::Vector2Return, "Some Ref").serializer
        val stringData = Json.encodeToString(parser, VectorExample.Vector2(1, 2))
        stringData `should be equal to` "[1,2]"
        Json.decodeFromString(parser, stringData) `should be equal to` VectorExample.Vector2(1, 2)
    }
    @Test
    fun decodeNode(){
        val parser = ReturnValue.createReference(::UserNode, "Test").serializer
        val json = "{\"firstName\": \"Test\",\"surname\": \"User\",\"password\": \"pass\"}"

        val json2 = "{\"firstName\": \"Test\",\"password\": \"pass\",\"surname\": \"User\"}"
        Json.decodeFromString(parser, json) `should be equal to` e2e.schemas.User("Test", "User", "pass")
        Json.decodeFromString(parser, json2) `should be equal to` e2e.schemas.User("Test", "User", "pass")
    }
    @Test
    fun decodeMultiple(){
        val data = "[\"Godfather, The\",4.487499999999999]"
        val parser = MultipleReturn2(ReturnValue.createReference(::StringReturn, "test"), ReturnValue.createReference(::DoubleReturn, "test"))
        Json.decodeFromString(parser.serializer, data) `should be equal to` ("Godfather, The" to 4.487499999999999)
    }
    @Test
    fun decodeData(){

        val data = "{\"row\": [\"Godfather, The\",4.487499999999999],\"meta\": [null, null]}"
        val parser = DataParser(MultipleReturn2(ReturnValue.createReference(::StringReturn, "test"), ReturnValue.createReference(::DoubleReturn, "test2")).serializer)
        println(Json.encodeToString(parser, "test" to 1.0))
        Json.decodeFromString(parser, data)
    }

    @Ignore
    @Test
    fun decodeRecord(){
        val data = File("C:\\Users\\james\\IdeaProjects\\neo4k\\src\\test\\resources\\response.json").readText()
        val parser = MultipleReturn2(ReturnValue.createReference(::StringReturn, "test"), ReturnValue.createReference(::DoubleReturn, "test"))
        val newParser = RecordParser(parser.serializer)
        Json.decodeFromString(newParser, data)
    }
    @Ignore
    @Test
    fun decodeResultSet(){
        val data = File("C:\\Users\\james\\IdeaProjects\\neo4k\\.idea\\httpRequests\\2023-01-02T183145.200.json").readText()
        val parser = MultipleReturn2(ReturnValue.createReference(::StringReturn, "test"), ReturnValue.createReference(::DoubleReturn, "test"))
        val newParser = ResultSetParser(parser.serializer)
        val parsed = Json.decodeFromString(newParser, data)
        println(parsed)
    }
    @Test
    fun ktorSingleTest(){
        val graph = Graph("neo4j", "test", "test", "localhost")
        val myQuery = query{
            val (movie, userRating) = match(::Movie `←-o` ::Rated `←-o` ::User)
            val (title, averageRating, numberOfRatings) =
                using(movie.title, avg(userRating.rating), count(userRating))
            where(numberOfRatings greaterThan 100)
            orderByDesc(averageRating)
            limit(25)
            title
        }.build()
        println(graph.myQuery())
    }

}