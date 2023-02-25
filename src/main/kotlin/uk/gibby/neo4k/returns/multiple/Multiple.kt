package uk.gibby.neo4k.returns.multiple


import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.empty.EmptyReturnInstance

fun QueryScope.none(): EmptyReturn = EmptyReturnInstance

fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>>QueryScope.many(first: A, second: B) = MultipleReturn2(first, second)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.many(first: A, second: B, third: C) =
    MultipleReturn3(first, second, third)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>, d, D: ReturnValue<d>> QueryScope.many(first: A, second: B, third: C, fourth: D) =
    MultipleReturn4(first, second, third, fourth)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>, d, D: ReturnValue<d>, e, E: ReturnValue<e>> QueryScope.many(first: A, second: B, third: C, fourth: D, fifth: E) =
    MultipleReturn5(first, second, third, fourth, fifth)