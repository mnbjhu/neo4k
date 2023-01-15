package e2e.labels

import uk.gibby.neo4k.core.Label
import uk.gibby.neo4k.core.LabelFactory
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.primitives.DoubleReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
import uk.gibby.neo4k.returns.util.ReturnScope
import util.GraphTest

class BasicTest: GraphTest() {

}

data class Person(val name: String)
open class PersonNode(val name: StringReturn): Node<Person>(){
    override fun ReturnScope.decode(): Person {
        return Person(::name.result())
    }
}

interface Actor: Label<Person>{
    companion object: LabelFactory<Person, Actor>(){
        override fun getLabel(): PersonNode = object: PersonNode(::StringReturn of "Test"), Actor{

        }
    }
}
interface Director: Label<Person>

sealed class Animal(val name: String){
    sealed class Dog(name: String, override val runningSpeed: Double): Animal(name), HasLegs{
        class Poodle(name: String, runningSpeed: Double): Dog(name, runningSpeed)
        class Huskey(name: String, runningSpeed: Double): Dog(name, runningSpeed)
    }
    class Fish(name: String): Animal(name)
    sealed class Insect(name: String): Animal(name){
        class Spider(name: String, override val runningSpeed: Double): Insect(name), HasLegs
        class Worm(name: String): Insect(name)
    }
}
interface HasLegs{
    val runningSpeed: Double
}
interface HasLegsLabel: Label<HasLegs>{
    val runningSpeed: DoubleReturn
}
sealed class AnimalNode<T: Animal>(val name: StringReturn): Node<T>(){
    sealed class Dog<T: Animal.Dog>(name: StringReturn, override val runningSpeed: DoubleReturn): AnimalNode<T>(name), HasLegsLabel{
        class Poodle(name: StringReturn, runningSpeed: DoubleReturn): Dog<Animal.Dog.Poodle>(name, runningSpeed)
        class Huskey(name: StringReturn, runningSpeed: DoubleReturn): Dog<Animal.Dog.Huskey>(name, runningSpeed)
    }
    sealed class Insect<T: Animal.Insect>(name: StringReturn): AnimalNode<T>(name){
        class Spider(name: StringReturn, override val runningSpeed: DoubleReturn): Insect<Animal.Insect.Spider>(name), HasLegsLabel
        class Worm(name: StringReturn): Insect<Animal.Insect.Worm>(name)
    }
    class Fish(name: StringReturn): AnimalNode<Animal.Fish>(name)
}

