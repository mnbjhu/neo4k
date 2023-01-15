package uk.gibby.neo4k.returns.multiple

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.returns.ReturnValue


data class MultipleReturn4<a,  A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>, d, D: ReturnValue<d>>(val first: A, val second: B, val third: C, val forth: D): ReturnValue<MultipleReturn4.Vec<a, b, c, d>>(),
    MultipleReturn<MultipleReturn4<a, A, b, B, c, C, d, D>> {
    override val serializer: KSerializer<Vec<a, b, c, d>> by lazy {
        object: KSerializer<Vec<a, b, c, d>> {
            override val descriptor = serialDescriptor<JsonArray>()
            override fun deserialize(decoder: Decoder): Vec<a, b, c, d> {
                var p1: a? = null
                var p2: b? = null
                var p3: c? = null
                var p4: d? = null
                val composite = decoder.beginStructure(descriptor)
                while (true){
                    when(composite.decodeElementIndex(descriptor)){
                        CompositeDecoder.DECODE_DONE -> break
                        0 -> p1 = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                        1 -> p2 = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                        2 -> p3 = composite.decodeSerializableElement(descriptor, 2, third.serializer)
                        3 -> p4 = composite.decodeSerializableElement(descriptor, 3, forth.serializer)
                    }
                }
                composite.endStructure(descriptor)
                return Vec(p1!!, p2!!, p3!!, p4!!)
            }

            override fun serialize(encoder: Encoder, value: Vec<a, b, c, d>) {
                val composite = encoder.beginStructure(descriptor)
                composite.encodeSerializableElement(descriptor, 0, first.serializer, value.first)
                composite.encodeSerializableElement(descriptor, 1, second.serializer, value.second)
                composite.encodeSerializableElement(descriptor, 2, third.serializer, value.third)
                composite.encodeSerializableElement(descriptor, 3, forth.serializer, value.forth)
                composite.endStructure(descriptor)
            }

        }
    }
    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}, ${third.getString()}, ${forth.getString()}"
    }

    override fun createReference(newRef: String): ReturnValue<Vec<a, b, c, d>> {
        TODO("Not yet implemented")
    }

    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn4<a, A, b, B, c, C, d, D> {
        return MultipleReturn4(values.first() as A, values[1] as B, values[2] as C, values[3] as D)
    }

    override fun createDummy(): ReturnValue<Vec<a, b, c, d>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Vec<a, b, c, d>): ReturnValue<Vec<a, b, c, d>> {
        TODO("Not yet implemented")
    }
    override fun getList(): List<ReturnValue<*>> {
        return listOf(first, second, third, forth)
    }

    data class Vec<a, b, c, d>(val first: a, val second: b, val third: c, val forth: d)
}