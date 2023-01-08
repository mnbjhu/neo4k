package uk.gibby.neo4k.queries

import kotlinx.serialization.KSerializer
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.ResultSetParser
import uk.gibby.neo4k.returns.MultipleReturn
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.SingleParser
import uk.gibby.neo4k.returns.empty.EmptyReturn

class QueryBuilder<p, P: ReturnValue<p>, r, R: ReturnValue<r>>(val args: P, val builder: QueryScope.(P) -> R){

    fun _build(): Query<p, P, r, R> {
        val scope = QueryScope()
        val returnValue = scope.builder(args)
        val resultParser = if(returnValue is MultipleReturn<*>) ResultSetParser(returnValue.serializer) else ResultSetParser(
            SingleParser(returnValue.serializer as KSerializer<r?>).serializer
        )
        val hasReturn = returnValue !is EmptyReturn
        val queryString = if (returnValue is EmptyReturn) "${scope.getString()} ${scope.getAfterString()}"
            else "${scope.getString()} RETURN ${returnValue.getString()} ${scope.getAfterString()}"
        return if(args is MultipleReturn<*>) Query(
            queryString,
            args.getList().map { it.serializer },
            resultParser,
            hasReturn
        )
            else Query(queryString, listOf(args.serializer), resultParser, hasReturn)
    }
}