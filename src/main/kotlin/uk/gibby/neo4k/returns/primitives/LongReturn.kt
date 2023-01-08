package uk.gibby.neo4k.returns.primitives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import uk.gibby.neo4k.returns.util.ReturnValueType


/**
 * Long return
 *
 * Represents a Redis Long and can be returned from graph queries
 *
 * @sample [e2e.types.primitive.Long.createLiteral]
 * @sample [e2e.types.primitive.Long.createAttribute]
 * @sample [e2e.types.primitive.Long.matchAttribute]
 */
class LongReturn(value: Long?): PrimitiveReturn<Long>(value) {
    override fun getPrimitiveString(from: Long): String = "$from"
    override fun encode(value: Long) = LongReturn(value)

    override val serializer: KSerializer<Long>
        get() = Long.serializer()

    override fun createReference(newRef: String): LongReturn{
        return LongReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = LongReturn(null).apply { type = ReturnValueType.ParserOnly("dummy") }

}