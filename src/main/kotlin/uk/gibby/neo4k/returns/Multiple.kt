package uk.gibby.neo4k.returns


import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.core.QueryScope

sealed interface MultipleReturn<T: MultipleReturn<T>>{
    fun getList(): List<ReturnValue<*>>
    fun parseList(values: List<ReturnValue<*>>): T
}


data class SingleParser<a: Any?, out A: KSerializer<a?>>(val inner: A){
     @OptIn(ExperimentalSerializationApi::class)
     val serializer: KSerializer<a?> by lazy {
         object: KSerializer<a?> {
            override val descriptor = serialDescriptor<JsonArray>()

            override fun deserialize(decoder: Decoder): a? {
                var f: a? = null
                val composite = decoder.beginStructure(descriptor)
                while (true){
                    when(composite.decodeElementIndex(descriptor)){
                        DECODE_DONE -> break
                        0 -> f = composite.decodeNullableSerializableElement(descriptor, 0, inner as KSerializer<Any?>) as a?
                    }
                }
                composite.endStructure(descriptor)
                return f
            }

            override fun serialize(encoder: Encoder, value: a?) {
                val composite = encoder.beginStructure(descriptor)
                composite.encodeNullableSerializableElement(descriptor, 0, inner, value)
                composite.endStructure(descriptor)
            }

        }
     }
}

object MultipleReturn0: MultipleReturn<MultipleReturn0> {
    override fun getList(): List<ReturnValue<*>> {
        return listOf()
    }

    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn0 {
        return this
    }
}

data class MultipleReturn1<a, A: ReturnValue<a>>(val first: A): ReturnValue<a>(), MultipleReturn<MultipleReturn1<a, A>>{
    override val serializer: KSerializer<a> by lazy { object: KSerializer<a> {
        override val descriptor = serialDescriptor<JsonArray>()

        override fun deserialize(decoder: Decoder): a {
            var f: a? = null
            val composite = decoder.beginStructure(descriptor)
            while (true){
                when(composite.decodeElementIndex(descriptor)){
                    DECODE_DONE -> break
                    0 -> f = composite.decodeSerializableElement(descriptor, 0, first.serializer)
                }
            }
            composite.endStructure(descriptor)
            return f!!
        }

        override fun serialize(encoder: Encoder, value: a){
            val composite = encoder.beginStructure(descriptor)
            composite.encodeSerializableElement(descriptor, 0, first.serializer, value)
            composite.endStructure(descriptor)
        }

    }}

    override fun getStructuredString(): String = first.getString()

    override fun createReference(newRef: String): ReturnValue<a> {
        TODO("Not yet implemented")
    }

    override fun createDummy(): ReturnValue<a> {
        TODO("Not yet implemented")
    }

    override fun encode(value: a): ReturnValue<a> {
        TODO("Not yet implemented")
    }

    override fun getList(): List<ReturnValue<*>> {
        return listOf(first)
    }

    override fun parseList(values: List<ReturnValue<*>>): MultipleReturn1<a, A> {
        return MultipleReturn1(values.first() as A)
    }
}
data class MultipleReturn2<a, A: ReturnValue<a>, b, B: ReturnValue<b>>(val first: A, val second: B): ReturnValue<Pair<a, b>>(), MultipleReturn<MultipleReturn2<a, A, b, B>> {
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

data class MultipleReturn3<a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>(val first: A, val second: B, val third: C): ReturnValue<Triple<a, b, c>>(), MultipleReturn<MultipleReturn3<a, A, b, B, c, C>> {
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
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>>QueryScope.many(first: A, second: B) = MultipleReturn2(first, second)
fun <a, A: ReturnValue<a>, b, B: ReturnValue<b>, c, C: ReturnValue<c>>QueryScope.many(first: A, second: B, third: C) =
    MultipleReturn3(first, second, third)
