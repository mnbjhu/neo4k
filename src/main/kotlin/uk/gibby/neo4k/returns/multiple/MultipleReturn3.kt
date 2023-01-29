package uk.gibby.neo4k.returns.multiple

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.returns.ReturnValue

data class MultipleReturn3<a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>(val first: A, val second: B, val third: C): Multiple<Triple<a, b, c>>(),
    MultipleReturn<MultipleReturn3<a, A, b, B, c, C>> {
    override val serializer: KSerializer<Triple<a, b, c>> by lazy {
        object: KSerializer<Triple<a, b, c>> {
            override val descriptor = serialDescriptor<JsonArray>()

            override fun deserialize(decoder: Decoder): Triple<a, b, c> {
                var f: a? = null
                var s: b? = null
                var t: c? = null
                val composite = decoder.beginStructure(descriptor)
                while (true){
                    when(composite.decodeElementIndex(descriptor)){
                        CompositeDecoder.DECODE_DONE -> break
                        0 -> f = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                        1 -> s = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                        2 -> t = composite.decodeSerializableElement(descriptor, 2, third.serializer)
                    }
                }
                composite.endStructure(descriptor)
                return Triple(f as a, s as b, t as c)
            }

            override fun serialize(encoder: Encoder, value: Triple<a, b, c>) {
                val composite = encoder.beginStructure(descriptor)
                composite.encodeSerializableElement(descriptor, 0, first.serializer, value.first)
                composite.encodeSerializableElement(descriptor, 1, second.serializer, value.second)
                composite.encodeSerializableElement(descriptor, 2, third.serializer, value.third)
                composite.endStructure(descriptor)
            }

        }
    }
    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}, ${third.getString()}"
    }
    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn3<a, A, b, B, c, C> {
        return MultipleReturn3(values.first() as A, values[1] as B, values[2] as C)
    }
    override fun createReference(newRef: String): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Triple<a, b, c>): ReturnValue<Triple<a, b, c>> {
        TODO("Not yet implemented")
    }

    override fun getList(): List<ReturnValue<*>> {
        return listOf(first, second, third)
    }

}