package uk.gibby.neo4k.returns.primitives

import org.neo4j.driver.internal.value.StringValue
import uk.gibby.neo4k.returns.util.ReturnValueType
import java.lang.ClassCastException
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue

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

    override fun encode(value: String) = StringReturn(value)
    override fun parse(value: Any?): String {
        try { return super.parse(value) } catch (_: ClassCastException){}
        return (value as StringValue).asString()
    }
    override fun createReference(newRef: String): StringReturn{
        return StringReturn(null).apply { type = ReturnValueType.Reference(newRef) }
    }
    override fun createDummy() = StringReturn(null).apply { type = ReturnValueType.ParserOnly }

}