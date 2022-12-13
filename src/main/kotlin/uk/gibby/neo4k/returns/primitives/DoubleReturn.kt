package uk.gibby.neo4k.returns.primitives

import org.neo4j.driver.internal.value.BooleanValue
import org.neo4j.driver.internal.value.FloatValue
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnValueType
import java.lang.ClassCastException
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue

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
    override fun parse(value: Any?): Double {
        try { return super.parse(value) } catch (_: ClassCastException){}
        return (value as FloatValue).asDouble()
    }
    override fun createReference(newRef: String): DoubleReturn {
        return DoubleReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }

    override fun createDummy() = DoubleReturn(null).apply { type = ReturnValueType.ParserOnly }
}