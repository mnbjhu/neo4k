package uk.gibby.neo4k.returns.primitives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.neo4j.driver.internal.value.BooleanValue
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnValueType
import java.lang.ClassCastException

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
    override fun parse(value: Any?): Boolean {
        try { return super.parse(value) } catch (_: ClassCastException){}
        return (value as BooleanValue).asBoolean()
    }

    override val serializer: KSerializer<Boolean>
        get() = Boolean.serializer()

    override fun createReference(newRef: String): BooleanReturn {
        return BooleanReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = BooleanReturn(null).apply { type = ReturnValueType.ParserOnly }

}