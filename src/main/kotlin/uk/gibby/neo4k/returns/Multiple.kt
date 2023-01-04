package uk.gibby.neo4k.returns

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.core.QueryScope

sealed interface MultipleReturn

data class SingleReturn<a, A: ReturnValue<a>>(val inner: A): ReturnValue<a>(){
     override val serializer: KSerializer<a> by lazy {
         object: KSerializer<a> {
            override val descriptor = serialDescriptor<JsonArray>()

            override fun deserialize(decoder: Decoder): a {
                var f: a? = null
                val composite = decoder.beginStructure(descriptor)
                while (true){
                    when(composite.decodeElementIndex(descriptor)){
                        DECODE_DONE -> break
                        0 -> f = composite.decodeSerializableElement(descriptor, 0, inner.serializer)
                    }
                }
                composite.endStructure(descriptor)
                return f!!
            }

            override fun serialize(encoder: Encoder, value: a) {
                val composite = encoder.beginStructure(descriptor)
                composite.encodeSerializableElement(descriptor, 0, inner.serializer, value)
                composite.endStructure(descriptor)
            }

        }
     }

    override fun getStructuredString(): String {
        TODO("Not yet implemented")
    }

    override fun parse(value: Any?): a {
        TODO("Not yet implemented")
    }

    override fun createReference(newRef: String): ReturnValue<a> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<a> {
        TODO("Not yet implemented")
    }

    override fun encode(value: a): ReturnValue<a> {
        TODO("Not yet implemented")
    }


}

data class MultipleReturn2<a, A: ReturnValue<a>, b, B: ReturnValue<b>>(val first: A, val second: B): ReturnValue<Pair<a, b>>(), MultipleReturn {
        override val serializer: KSerializer<Pair<a, b>> by lazy { object: KSerializer<Pair<a, b>> {
        override val descriptor = serialDescriptor<JsonArray>()

        override fun deserialize(decoder: Decoder): Pair<a, b> {
            var f: a? = null
            var s: b? = null
            val composite = decoder.beginStructure(descriptor)
            while (true){
                when(composite.decodeElementIndex(descriptor)){
                    DECODE_DONE -> break
                    0 -> f = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                    1 -> s = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                }
            }
            composite.endStructure(descriptor)
            return f!! to s!!
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
    override fun parse(value: Any?): Pair<a, b> {
        val list = value as List<*>
        return first.parse(list[0]) to second.parse(list[1])
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
}

data class MultipleReturn3<a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>(val first: A, val second: B, val third: C): ReturnValue<Triple<a, b, c>>(), MultipleReturn {
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
                        DECODE_DONE -> break
                        0 -> f = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                        1 -> s = composite.decodeSerializableElement(descriptor, 1, second.serializer)
                        2 -> t = composite.decodeSerializableElement(descriptor, 2, third.serializer)
                    }
                }
                composite.endStructure(descriptor)
                return Triple(f!!, s!!, t!!)
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
    override fun parse(value: Any?): Triple<a, b, c> {
        val list = value as List<*>
        return Triple(first.parse(list[0]), second.parse(list[1]), third.parse(list[2]))
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

}
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>>QueryScope.many(first: A, second: B) = MultipleReturn2(first, second)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.many(first: A, second: B, third: C) =
    MultipleReturn3(first, second, third)
