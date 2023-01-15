package uk.gibby.neo4k.returns.multiple

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.returns.ReturnValue


data class MultipleReturn5<a,  A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>, d, D: ReturnValue<d>, e, E: ReturnValue<e>>(val first: A, val second: B, val third: C, val forth: D, val fifth: E): ReturnValue<MultipleReturn5.Vec<a, b, c, d, e>>(),
    MultipleReturn<MultipleReturn5<a, A, b, B, c, C, d, D, e, E>> {
    override val serializer: KSerializer<Vec<a, b, c, d, e>> by lazy {
        object: KSerializer<Vec<a, b, c, d, e>> {
            override val descriptor = serialDescriptor<JsonArray>()
            override fun deserialize(decoder: Decoder): Vec<a, b, c, d, e> {
                var p1: a? = null
                var p2: b? = null
                var p3: c? = null
                var p4: d? = null
                var p5: e? = null
                val composite = decoder.beginStructure(descriptor)
                while (true){
                    when(composite.decodeElementIndex(descriptor)){
                        CompositeDecoder.DECODE_DONE -> break
                        0 -> p1 = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                        1 -> p2 = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                        2 -> p3 = composite.decodeSerializableElement(descriptor, 2, third.serializer)
                        3 -> p4 = composite.decodeSerializableElement(descriptor, 3, forth.serializer)
                        4 -> p5 = composite.decodeSerializableElement(descriptor, 4, fifth.serializer)
                    }
                }
                composite.endStructure(descriptor)
                return Vec(p1!!, p2!!, p3!!, p4!!, p5!!)
            }

            override fun serialize(encoder: Encoder, value: Vec<a, b, c, d, e>) {
                val composite = encoder.beginStructure(descriptor)
                composite.encodeSerializableElement(descriptor, 0, first.serializer, value.first)
                composite.encodeSerializableElement(descriptor, 1, second.serializer, value.second)
                composite.encodeSerializableElement(descriptor, 2, third.serializer, value.third)
                composite.encodeSerializableElement(descriptor, 3, forth.serializer, value.forth)
                composite.encodeSerializableElement(descriptor, 4, fifth.serializer, value.fifth)
                composite.endStructure(descriptor)
            }

        }
    }
    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}, ${third.getString()}, ${forth.getString()}"
    }

    override fun createReference(newRef: String): ReturnValue<Vec<a, b, c, d, e>> {
        TODO("Not yet implemented")
    }

    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn5<a, A, b, B, c, C, d, D, e, E> {
        return MultipleReturn5(values.first() as A, values[1] as B, values[2] as C, values[3] as D, values[4] as E)
    }

    override fun createDummy(): ReturnValue<Vec<a, b, c, d, e>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Vec<a, b, c, d, e>): ReturnValue<Vec<a, b, c, d, e>> {
        TODO("Not yet implemented")
    }
    override fun getList(): List<ReturnValue<*>> {
        return listOf(first, second, third, forth)
    }

    data class Vec<a, b, c, d, e>(val first: a, val second: b, val third: c, val forth: d, val fifth: e)
}