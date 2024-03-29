package uk.gibby.neo4k.returns.empty

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import uk.gibby.neo4k.returns.ReturnValue

abstract class EmptyReturn: ReturnValue<Unit>() {

    override val serializer: KSerializer<Unit> = Unit.serializer()
    override fun getStructuredString(): String {
        throw Exception("Return is empty")
    }

    override fun encode(value: Unit): ReturnValue<Unit> {
        throw Exception("Return is empty")
    }

    override fun createReference(newRef: String) = throw NotImplementedError()
    override fun createDummy() = throw NotImplementedError()
}