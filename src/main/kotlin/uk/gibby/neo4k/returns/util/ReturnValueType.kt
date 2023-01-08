package uk.gibby.neo4k.returns.util
sealed class ReturnValueType{
    object Instance: ReturnValueType()
    class ParserOnly(val name: String): ReturnValueType()
    class Reference(val ref: String): ReturnValueType()
}