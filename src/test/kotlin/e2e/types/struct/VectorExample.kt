package e2e.types.struct

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.generic.StructReturn
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.LongReturn
import uk.gibby.neo4k.returns.util.ReturnScope
import util.GraphTest

class VectorExample: GraphTest() {
    data class Vector2(val x: Long, val y: Long)
    class Vector2Return(val x: LongReturn, val y: LongReturn): StructReturn<Vector2>() {
        override fun encode(value: Vector2) = Vector2Return(
            ::LongReturn of value.x,
            ::LongReturn of value.y
        )
        override fun ReturnScope.decode() = Vector2(
            ::x.result(),
            ::y.result()
        )
    }
    class Vector2Node(val innerVector: Vector2Return): UnitNode()

    @Test
    fun createLiteral(){
        graph.query { ::Vector2Return of Vector2(1, 2) } `should be equal to` listOf(Vector2(1, 2))
    }

    @Test
    fun createNullLiteral() {
        graph.query { nullable(::Vector2Return) of null } `should be equal to` listOf(null)
    }

    @Test
    fun createAttribute(){
        graph.query {
            val newNode = create(::Vector2Node{ it[::innerVector] = Vector2(1, 2) })
            newNode.innerVector
        } `should be equal to` listOf(Vector2(1, 2))
    }

    @Test
    fun matchAttribute(){
        createAttribute()
        graph.query {
            val newNode = match(::Vector2Node{ it[::innerVector] = Vector2(1, 2) })
            newNode.innerVector
        } `should be equal to` listOf(Vector2(1, 2))
    }

}