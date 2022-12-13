package uk.gibby.neo4k.returns.primitives

import org.neo4j.driver.internal.value.IntegerValue
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnValueType
import java.lang.ClassCastException


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
    override fun parse(value: Any?): Long {
        try {
            return super.parse(value)
        } catch (_: ClassCastException){}
        return (value as IntegerValue).asLong()
    }

    override fun createReference(newRef: String): LongReturn{
        return LongReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = LongReturn(null).apply { type = ReturnValueType.ParserOnly }

}