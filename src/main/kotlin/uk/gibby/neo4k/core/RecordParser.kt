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
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.JsonElement
import java.lang.Exception

data class ResultSet<T>(val data: List<List<T>>, val errors: List<NeoError>, val notifications: List<Notification>)
class RecordParser<T, U: KSerializer<T>>(private val result: U): KSerializer<List<T>> {
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


class DataParser<T, U: KSerializer<T>>(private val result: U): KSerializer<T> {
    private val listKSerializer = ListSerializer(JsonElement.serializer().nullable)

    override fun deserialize(decoder: Decoder): T {
        val context = decoder.beginStructure(descriptor)
        var r: T? = null
        while (true){
            when(context.decodeElementIndex(descriptor)){
                0 -> r = context.decodeSerializableElement(descriptor, 0, result)
                DECODE_DONE -> break
                else -> { println(context.decodeSerializableElement(descriptor, 1, listKSerializer)) }
            }
        }
        context.endStructure(descriptor)
        return r as T
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("data"){
        element("row", result.descriptor)
        element("meta", String.serializer().nullable.descriptor)
    }

    override fun serialize(encoder: Encoder, value: T) {
        val context = encoder.beginStructure(descriptor)
        context.encodeSerializableElement(descriptor, 0, result, value)
        context.endStructure(descriptor)
    }
}
class ResultSetParser<T, U: KSerializer<T>>(private val result: U): KSerializer<ResultSet<T>> {
    private val dataParser = ListSerializer(RecordParser(result))

    private val notificationsParser = ListSerializer(Notification.serializer())

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("result_set"){
        element("results", ListSerializer(RecordParser(result)).descriptor)
        element("errors", ListSerializer(String.serializer()).descriptor)
        element("notifications", notificationsParser.descriptor)
    }
    private val errorParser = ListSerializer(NeoError.serializer())

    override fun deserialize(decoder: Decoder): ResultSet<T> {
        var data: List<List<T>>? = null
        var errors: List<NeoError>? = null
        var notifications: List<Notification>? = null
        decoder.decodeStructure(descriptor){
            while (true){
                when(decodeElementIndex(descriptor)){
                    0 -> data = decodeSerializableElement(descriptor, 0, dataParser)
                    1 -> errors = decodeSerializableElement(descriptor, 1, errorParser)
                    2 -> notifications = decodeSerializableElement(descriptor, 2, notificationsParser)
                    DECODE_DONE -> break
                    else -> { throw Exception("Failed to parse JSON response") }
                }
            }
        }
        return ResultSet(data ?: emptyList(), errors ?: emptyList(), notifications ?: emptyList())
    }
    override fun serialize(encoder: Encoder, value: ResultSet<T>) {
        val context = encoder.beginStructure(descriptor)
        context.encodeSerializableElement(descriptor, 0, dataParser, value.data)
        context.endStructure(descriptor)
    }
}
