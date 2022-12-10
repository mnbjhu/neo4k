package uk.gibby.neo4k.returns.util
sealed class ReturnValueType{
    object Instance: ReturnValueType()
    object ParserOnly: ReturnValueType()
    class Reference(val ref: String): ReturnValueType()
}