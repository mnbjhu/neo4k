package uk.gibby.neo4k.returns.multiple


import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.returns.ReturnValue


fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>>QueryScope.many(first: A, second: B) = MultipleReturn2(first, second)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.many(first: A, second: B, third: C) =
    MultipleReturn3(first, second, third)
