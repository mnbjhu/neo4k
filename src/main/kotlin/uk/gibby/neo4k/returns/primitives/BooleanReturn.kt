package uk.gibby.neo4k.returns.primitives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import uk.gibby.neo4k.returns.util.ReturnValueType

/**
 * Boolean return
 *
 * Represents a Redis Boolean and can be returned from graph queries
 *
 * @sample [e2e.types.primitive.Boolean.createLiteral]
 * @sample [e2e.types.primitive.Boolean.createAttribute]
 * @sample [e2e.types.primitive.Boolean.matchAttribute]
 */
class BooleanReturn(value: Boolean?): PrimitiveReturn<Boolean>(value) {
    override fun getPrimitiveString(from: Boolean): String = "$from"
    override fun encode(value: Boolean) = BooleanReturn(value)

    override val serializer: KSerializer<Boolean>
        get() = Boolean.serializer()

    override fun createReference(newRef: String): BooleanReturn {
        return BooleanReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = BooleanReturn(null).apply { type = ReturnValueType.ParserOnly("dummy") }

}