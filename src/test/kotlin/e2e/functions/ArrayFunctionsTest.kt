package e2e.functions

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.core.array
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.functions.conditions.primitive.array_return.range
import uk.gibby.neo4k.functions.conditions.primitive.long_return.times
import uk.gibby.neo4k.functions.conditions.primitive.string_return.contains
import uk.gibby.neo4k.returns.primitives.StringReturn
import util.GraphTest

class ArrayFunctionsTest: GraphTest() {
    @Test
    fun testAll(){
        graph.query {
            val myList = array(::StringReturn) of listOf("first", "second", "third")
            myList.all { it contains "i" }
        } `should be equal to` listOf(false)
    }
    @Test
    fun testAny(){
        graph.query {
            val myList = array(::StringReturn) of listOf("first", "second", "third")
            myList.any { it contains "i" }
        } `should be equal to` listOf(true)
    }
    @Test
    fun testSingle1(){
        graph.query {
            val myList = array(::StringReturn) of listOf("first", "second", "third")
            myList.single { it contains "i" }
        } `should be equal to` listOf(false)
    }
    @Test
    fun testSingle2(){
        graph.query {
            val myList = array(::StringReturn) of listOf("first", "second", "third")
            myList.single { it contains "o" }
        } `should be equal to` listOf(true)
    }
    @Test
    fun testNone(){
        graph.query {
            val myList = array(::StringReturn) of listOf("first", "second", "third")
            myList.none { it contains "X" }
        } `should be equal to` listOf(true)
    }

    @Test
    fun testRange(){
        graph.query {
            range(1, 3)
        } `should be equal to` listOf(listOf(1L,2L,3L))
    }

    @Test
    fun testRangeWithStep(){
        graph.query {
            range(1, 5, 2)
        } `should be equal to` listOf(listOf(1L,3L,5L))
    }
    @Test
    fun testMap(){
        graph.query {
            range(1, 5).map { it * it }
        } `should be equal to` listOf(listOf(1L, 4L, 9L, 16L, 25L))
    }
    @Test
    fun testMap2(){
        graph.query {
            range(1, 5).map { range(1, it) }
        } `should be equal to` listOf(
            listOf(
                listOf(1L),
                listOf(1L, 2L),
                listOf(1L, 2L, 3L),
                listOf(1L, 2L, 3L, 4L),
                listOf(1L, 2L, 3L, 4L, 5L)
            )
        )
    }
}