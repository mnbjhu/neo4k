package e2e.types.primitive


import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import util.GraphTest

class Double: GraphTest() {
    class DoubleNode(val innerDouble: DoubleReturn): UnitNode()
    @Test
    fun createLiteral(){
        graph.query { ::DoubleReturn of 23.0 } `should be equal to` listOf(23.0)
    }

    @Test
    fun createNullLiteral(){
        graph.query { nullable(::DoubleReturn) of null } `should be equal to` listOf(null)
    }

    @Test
    fun createAttribute(){
        graph.query {
            val newNode = create(::DoubleNode{ it[innerDouble] = 23.0 })
            newNode.innerDouble
        } `should be equal to` listOf(23.0)
    }

    @Test
    fun matchAttribute(){
        createAttribute()
        graph.query {
            val newNode = match(::DoubleNode{ it[innerDouble] = 23.0 })
            newNode.innerDouble
        } `should be equal to` listOf(23.0)
    }
}