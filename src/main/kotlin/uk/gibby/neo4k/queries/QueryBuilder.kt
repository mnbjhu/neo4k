package uk.gibby.neo4k.queries

import kotlinx.serialization.KSerializer
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.ResultSetParser
import uk.gibby.neo4k.returns.multiple.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.empty.EmptyReturn

class QueryBuilder<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val args: P, val builder: QueryScope.(P) -> R){

    fun _build(): Query<p, P, r, R> {
        val scope = QueryScope()
        val returnValue = scope.builder(args)
        val hasReturn = returnValue !is EmptyReturn
        val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
            else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
        return if(args is MultipleReturn<*>) Query(
            queryString,
            args.getList().map { it.serializer },
            returnValue,
            hasReturn
        )
            else Query(queryString, listOf(args.serializer), returnValue, hasReturn)
    }
}