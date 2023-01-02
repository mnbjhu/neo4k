package uk.gibby.neo4k.returns.generic

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import org.neo4j.driver.internal.value.ListValue
import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.ParamMap
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import java.lang.ClassCastException
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

abstract class StructReturn<T>: DataType<T>(){
    val elements by lazy { this::class.memberProperties
        .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
        .map { it.name to (it.call(this) as ReturnValue<*>) }
    }
    val indexMap by lazy{ elements.mapIndexed { index, pair -> pair.second to index }.toMap() }
    @OptIn(ExperimentalSerializationApi::class)
    override val serializer: KSerializer<T> by lazy { object : KSerializer<T>{
        override val descriptor = serialDescriptor<JsonArray>()

        override fun deserialize(decoder: Decoder): T {
            val composite = decoder.beginStructure(descriptor)
            //if (composite.decodeSequentially()){
            val map = elements.map { element ->
                val index = composite.decodeElementIndex(descriptor)
                element.first to composite.decodeSerializableElement(descriptor, index, element.second.serializer)
            }.toMap()
            composite.endStructure(descriptor)
                return ReturnScope(map).decode()
            //} else throw Exception("Failed to deserialize JSON")
        }


        override fun serialize(encoder: Encoder, value: T) {
            val params = StructParamMap()
            params.encodeStruct(value)
            val values = params.getList()
            encoder.beginStructure(descriptor).apply{

                values.forEachIndexed { index, any ->
                    encodeSerializableElement(descriptor, index, elements[index].second.serializer as KSerializer<Any?>, any)
                }
            }.endStructure(descriptor)

        }

    }}
    override fun getStructuredString(): String {
        return this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .joinToString(prefix = "[", postfix = "]") {
                val value = it.call(this) as ReturnValue<*>
                value.getString()
            }
    }
    override fun encode(value: T): StructReturn<T>{
        val params = StructParamMap()
        params.encodeStruct(value)
        val args = params.getList().mapIndexed { index,  any ->
            (elements[index].second as ReturnValue<Any?>).encode(any)
        }
        return this::class.primaryConstructor!!.call(args)
    }
    abstract fun StructParamMap.encodeStruct(value: T)
    override fun parse(value: Any?): T {
        val values = try {
            value as List<*>} catch (_: ClassCastException){(value as ListValue).asList()}.iterator()
        val map = this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
            .associate { it.name to values.next() }
        return ReturnScope(map).decode()
    }
    abstract fun ReturnScope.decode(): T

    override fun createReference(newRef: String) = Companion.createReference(this::class.createType(), newRef) as StructReturn<T>
    override fun createDummy() = createDummy(this::class.createType()) as StructReturn<T>
    inner class StructParamMap(){
        private val map: MutableList<Any?> = MutableList(elements.size){ null }
        operator fun <T, U: ReturnValue<T>>U.get(value: T){
            map[indexMap[this]!!] = value
        }
        fun getList() = map.toList()
    }
}


