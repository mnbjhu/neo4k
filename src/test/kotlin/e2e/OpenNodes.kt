package e2e

import org.amshove.kluent.`should contain same`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.returns.graph.entities.UnitNode
import uk.gibby.neo4k.returns.primitives.StringReturn
import util.GraphTest

open class Animal(val name: StringReturn): UnitNode()

class Dog(name: StringReturn, val breed: StringReturn): Animal(name)

class Fish(name: StringReturn, val waterType: StringReturn): Animal(name)

class OpenNodes: GraphTest() {
    @Test
    fun create(){
        graph.query {
            create(::Dog{ it[::name] = "Scooby Doo"; it[::breed] = "Great Dane" })
            create(::Dog{ it[::name] = "Santa's Little Helper"; it[::breed] = "Greyhound" })
            create(::Fish{ it[::name] = "Nemo"; it[::waterType] = "Saltwater" })
        }
    }
    @Test
    fun matchAnimal(){
        create()
        graph.query {
            val animal = match(::Animal)
            animal.name
        } `should contain same` listOf("Scooby Doo", "Santa's Little Helper", "Nemo")
    }
    @Test
    fun matchDog(){
        create()
        graph.query {
            val animal = match(::Dog)
            animal.name
        } `should contain same` listOf("Scooby Doo", "Santa's Little Helper")
    }
    @Test
    fun matchFish(){
        create()
        graph.query {
            val animal = match(::Fish)
            animal.name
        } `should contain same` listOf("Nemo")
    }
}