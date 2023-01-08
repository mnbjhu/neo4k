package uk.gibby.neo4k.returns.primitives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import uk.gibby.neo4k.returns.util.ReturnValueType

/**
 * String return
 *
 * Represents a Redis String and can be returned from graph queries
 *
 * @sample [e2e.types.primitive.String.createLiteral]
 * @sample [e2e.types.primitive.String.createAttribute]
 * @sample [e2e.types.primitive.String.matchAttribute]
 */
class StringReturn(value: String?): PrimitiveReturn<String>(value) {
    override fun getPrimitiveString(from: String): String = "'${from
        .replace("\\", "\\\\")
        .replace("'", "\\'")}'"
        .replace("\\", "\\\\")

    override fun encode(value: String) = StringReturn(value)

    override val serializer: KSerializer<String>
        get() = String.serializer()

    override fun createReference(newRef: String): StringReturn{
        return StringReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = StringReturn(null).apply { type = ReturnValueType.ParserOnly("dummy") }

}