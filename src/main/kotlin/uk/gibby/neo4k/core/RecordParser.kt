package uk.gibby.neo4k.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import uk.gibby.neo4k.returns.ReturnValue

class RecordParser<T, U: ReturnValue<T>>(val result: U): KSerializer<List<T>> {
    override fun deserialize(decoder: Decoder): List<T> {
        val context = decoder.beginStructure(descriptor)
        var r: List<T>? = null
        while (true){
            when(context.decodeElementIndex(descriptor)){
                1 -> r = context.decodeSerializableElement(descriptor, 1, ListSerializer(DataParser(result)))
                DECODE_DONE -> break
                else -> { context.decodeSerializableElement(descriptor, 0, ListSerializer(String.serializer())) }
            }
        }
        context.endStructure(descriptor)
        return r!!
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("result"){
        element("columns", ListSerializer(String.serializer()).descriptor)
        element("data", ListSerializer(DataParser(result)).descriptor)
    }

    override fun serialize(encoder: Encoder, value: List<T>) {
        val context = encoder.beginStructure(descriptor)
        context.encodeSerializableElement(descriptor, 0, ListSerializer(DataParser(result)), value)
        context.endStructure(descriptor)
    }
}


class DataParser<T, U: ReturnValue<T>>(val result: U): KSerializer<T> {
    override fun deserialize(decoder: Decoder): T {
        val context = decoder.beginStructure(descriptor)
        var r: T? = null
        while (true){
            when(context.decodeElementIndex(descriptor)){
                0 -> r = context.decodeSerializableElement(descriptor, 0, result.serializer)
                DECODE_DONE -> break
                else -> { context.decodeSerializableElement(descriptor, 1, ListSerializer(String.serializer().nullable)) }
            }
        }
        context.endStructure(descriptor)
        return r!!
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("data"){
        element("row", result.serializer.descriptor)
        element("meta", String.serializer().nullable.descriptor)
    }

    override fun serialize(encoder: Encoder, value: T) {
        val context = encoder.beginStructure(descriptor)
        context.encodeSerializableElement(descriptor, 0, result.serializer, value)
        context.endStructure(descriptor)
    }
}
class ResultSetParser<T, U: ReturnValue<T>>(private val result: U): KSerializer<List<List<T>>> {
    override fun deserialize(decoder: Decoder): List<List<T>> {
        val context = decoder.beginStructure(descriptor)
        var r: List<List<T>>? = null
        while (true){
            when(context.decodeElementIndex(descriptor)){
                0 -> r = context.decodeSerializableElement(descriptor, 0, ListSerializer(RecordParser(result)))
                DECODE_DONE -> break
                else -> { context.decodeSerializableElement(descriptor, 1, ListSerializer(String.serializer())) }
            }
        }
        context.endStructure(descriptor)
        return r!!
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("result_set"){
        element("results", ListSerializer(RecordParser(result)).descriptor)
        element("errors", ListSerializer(String.serializer()).descriptor)
    }

    override fun serialize(encoder: Encoder, value: List<List<T>>) {
        val context = encoder.beginStructure(descriptor)
        context.encodeSerializableElement(descriptor, 0, ListSerializer(RecordParser(result)), value)
        context.endStructure(descriptor)
    }
}
