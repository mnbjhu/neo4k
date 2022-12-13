package e2e.types.primitive


import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.LongReturn
import util.GraphTest

class Long: GraphTest() {
    class LongNode(val innerLong: LongReturn): UnitNode()
    @Test
    fun createLiteral(){
        graph.query { ::LongReturn of 23 } `should be equal to` listOf(23)
    }

    @Test
    fun createNullLiteral(){
        graph.query { nullable(::LongReturn) of null } `should be equal to` listOf(null)
    }

    @Test
    fun createAttribute(){
        graph.query {
            val newNode = create(::LongNode{ it[innerLong] = 23 })
            newNode.innerLong
        } `should be equal to` listOf(23)
    }

    @Test
    fun matchAttribute(){
        createAttribute()
        graph.query {
            val newNode = match(::LongNode{ it[innerLong] = 23 })
            newNode.innerLong
        } `should be equal to` listOf(23)
    }
}