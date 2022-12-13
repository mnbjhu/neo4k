package e2e.types.primitive


import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import util.GraphTest

class Boolean: GraphTest() {
    class BooleanNode(val innerBoolean: BooleanReturn): UnitNode()
    @Test
    fun createLiteral(){
        graph.query { ::BooleanReturn of false } `should be equal to` listOf(false)
    }

    @Test
    fun createNullLiteral(){
        graph.query { nullable(::BooleanReturn) of null } `should be equal to` listOf(null)
    }

    @Test
    fun createAttribute(){
        graph.query {
            val newNode = create(::BooleanNode{ it[innerBoolean] = false })
            newNode.innerBoolean
        } `should be equal to` listOf(false)
    }

    @Test
    fun matchAttribute(){
        createAttribute()
        graph.query {
            val newNode = match(::BooleanNode{ it[innerBoolean] = false })
            newNode.innerBoolean
        } `should be equal to` listOf(false)
    }
}