package uk.gibby.neo4k.returns

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray

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
                        CompositeDecoder.DECODE_DONE -> break
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