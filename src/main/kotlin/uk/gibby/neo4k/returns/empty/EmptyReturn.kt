package uk.gibby.neo4k.returns.empty

import uk.gibby.neo4k.returns.ReturnValue

abstract class EmptyReturn: ReturnValue<Unit>() {
    override fun getStructuredString(): String {
        throw Exception("Return is empty")
    }

    override fun parse(value: Any?) {
        throw Exception("Return is empty")
    }

    override fun encode(value: Unit): ReturnValue<Unit> {
        throw Exception("Return is empty")
    }

    override fun createReference(newRef: String) = throw NotImplementedError()
    override fun createDummy() = throw NotImplementedError()
}