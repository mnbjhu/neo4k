package uk.gibby.neo4k.returns.multiple

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.returns.ReturnValue

data class MultipleReturn2<a, A: ReturnValue<a>, b, B: ReturnValue<b>>(val first: A, val second: B): Multiple<Pair<a, b>>(),
    MultipleReturn<MultipleReturn2<a, A, b, B>> {
        override val serializer: KSerializer<Pair<a, b>> by lazy { object: KSerializer<Pair<a, b>> {
        override val descriptor = serialDescriptor<JsonArray>()

        override fun deserialize(decoder: Decoder): Pair<a, b> {
            var f: a? = null
            var s: b? = null
            val composite = decoder.beginStructure(descriptor)
            while (true){
                when(composite.decodeElementIndex(descriptor)){
                    CompositeDecoder.DECODE_DONE -> break
                    0 -> f = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                    1 -> s = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                }
            }
            composite.endStructure(descriptor)
            return f as a to s as b
        }

        override fun serialize(encoder: Encoder, value: Pair<a, b>) {
            val composite = encoder.beginStructure(descriptor)
            composite.encodeSerializableElement(descriptor, 0, first.serializer, value.first)
            composite.encodeSerializableElement(descriptor, 1, second.serializer, value.second)
            composite.endStructure(descriptor)
        }

    }}

    override fun getStructuredString(): String {
        return "${first.getString()}, ${second.getString()}"
    }

    override fun createReference(newRef: String): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }

    override fun encode(value: Pair<a, b>): ReturnValue<Pair<a, b>> {
        TODO("Not yet implemented")
    }

    override fun getList(): List<ReturnValue<*>> {
        return listOf(first, second)
    }

    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn2<a, A, b, B> {
        return MultipleReturn2(values.first() as A, values[1] as B)
    }
}