package uk.gibby.neo4k.returns.primitives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import uk.gibby.neo4k.returns.util.ReturnValueType

/**
 * Double return
 *
 * Represents a Redis Double and can be returned from graph queries
 *
 * @sample [e2e.types.primitive.Double.createLiteral]
 * @sample [e2e.types.primitive.Double.createAttribute]
 * @sample [e2e.types.primitive.Double.matchAttribute]
 */

class DoubleReturn(value: Double?): PrimitiveReturn<Double>(value) {
    override fun getPrimitiveString(from: Double): String = "$from"
    override fun encode(value: Double) = DoubleReturn(value)
    override val serializer: KSerializer<Double>
        get() = Double.serializer()

    override fun createReference(newRef: String): DoubleReturn {
        return DoubleReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }

    override fun createDummy() = DoubleReturn(null).apply { type = ReturnValueType.ParserOnly("dummy") }
}