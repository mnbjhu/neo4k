package uk.gibby.neo4k.clauses

import uk.gibby.neo4k.core.NameCounter
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.MultipleReturn2
import uk.gibby.neo4k.returns.MultipleReturn3
import uk.gibby.neo4k.returns.ReturnValue

class WithAs<U: ReturnValue<*>>(private val map: Map<U, U>) : Claus() {
    constructor(vararg entries: Pair<U, U>): this(entries.toMap())
    override fun getString(): String {
        return map.toList().joinToString(prefix = "WITH ") {  "${it.first.getString()} AS ${it.second.getString() }" }
    }
    companion object{
        fun <U: ReturnValue<*>> QueryScope.using(value: U): U =
            (value.createReference(NameCounter.next()) as U).also { addStatement(WithAs(value to it)) }


        fun <u, U: ReturnValue<u>, v, V: ReturnValue<v>> QueryScope.using(first: U, second: V): MultipleReturn2<u, U, v, V>{
            val firstNewRef = first.createReference(NameCounter.next()) as U
            val secondNewRef = second.createReference(NameCounter.next()) as V
            addStatement(WithAs(first to firstNewRef, second to secondNewRef))
            return MultipleReturn2(firstNewRef, secondNewRef)
        }
        fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.using(first: A, second: B, third: C): MultipleReturn3<a, A, b, B, c, C>{
            val firstNewRef = first.createReference(NameCounter.next()) as A
            val secondNewRef = second.createReference(NameCounter.next()) as B
            val thirdNewRef = third.createReference(NameCounter.next()) as C

            addStatement(WithAs(first to firstNewRef, second to secondNewRef, third to thirdNewRef))
            return MultipleReturn3(firstNewRef, secondNewRef, thirdNewRef)
        }
    }
}
