package uk.gibby.neo4k.returns.empty

import kotlinx.serialization.KSerializer
import uk.gibby.neo4k.returns.ReturnValue

abstract class EmptyReturn: ReturnValue<Unit>() {

    override val serializer: KSerializer<Unit>
        get() = TODO("Not yet implemented")
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