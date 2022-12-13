package e2e.types.primitive

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.StringReturn
import util.GraphTest

class String: GraphTest() {
    class StringNode(val innerString: StringReturn): UnitNode()
    @Test
    fun createLiteral(){
        graph.query { ::StringReturn of "Test" } `should be equal to` listOf("Test")
    }

    @Test
    fun createNullLiteral(){
        graph.query { nullable(::StringReturn) of null } `should be equal to` listOf(null)
    }

    @Test
    fun createAttribute(){
        graph.query {
            val newNode = create(::StringNode{ it[innerString] = "Test Attr" })
            newNode.innerString
        } `should be equal to` listOf("Test Attr")
    }

    @Test
    fun matchAttribute(){
        createAttribute()
        graph.query {
            val newNode = match(::StringNode{ it[innerString] = "Test Attr" })
            newNode.innerString
        } `should be equal to` listOf("Test Attr")
    }

    @Test
    fun testEdgeCases(){
        val edgeCases = listOf("null", "NULL", "String with 'quotes'", "String with \\'escaped quotes\\'")
        edgeCases.forEach {
            graph.query { nullable(::StringReturn) of it } `should be equal to` listOf(it)
        }
    }
}