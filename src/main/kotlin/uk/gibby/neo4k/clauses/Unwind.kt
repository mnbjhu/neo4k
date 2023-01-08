package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.generic.ArrayReturn

class Unwind<T, U: ReturnValue<T>>(private val array: ArrayReturn<T, U>, private val element: U): Clause(){
    override fun getString(): String {
        return "UNWIND ${array.getString()} AS ${element.getString()}"
    }
    companion object{
        fun <T, U: ReturnValue<T>>QueryScope.unwind(array: ArrayReturn<T, U>): U{
            val newElementRef = NameCounter.next()
            val newElement = array.inner.createReference(newElementRef) as U
            addStatement(Unwind(array, newElement))
            return newElement
        }
    }
}