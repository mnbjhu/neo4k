package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance
import uk.gibby.neo4k.returns.generic.ArrayReturn

class ForEach<T, U: ReturnValue<T>>(private val array: ArrayReturn<T, U>, private val query: QueryScope.(U) -> Unit): Clause(){
    private val element = array.inner.createReference(NameCounter.next()) as U
    override fun getString(): String {
        val scope = QueryScope()
        scope.query(element)
        return "FOREACH(${element.getString()} IN ${array.getString()}|${scope.getString()})"
    }
    companion object{
        fun <T, U: ReturnValue<T>>QueryScope.forEach(array: ArrayReturn<T, U>, action: QueryScope.(U) -> Unit): EmptyReturn {
            addStatement(ForEach(array, action))
            return EmptyReturnInstance
        }
    }
}
