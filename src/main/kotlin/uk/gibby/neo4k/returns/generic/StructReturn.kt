package uk.gibby.neo4k.returns.generic

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.JsonArray
import uk.gibby.neo4k.returns.DataType
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import uk.gibby.neo4k.returns.util.ReturnValueType
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
    val indexMap by lazy{ elements.mapIndexed { index, pair -> pair.first to index }.toMap() }
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
            with(createDummy()){
                params.encodeStruct(value)
            }
            val values = params.getList()
            encoder.beginStructure(descriptor).apply{
                values.forEachIndexed { index, any ->
                    encodeSerializableElement(descriptor, index, elements[index].second.serializer as KSerializer<Any?>, (any.second))
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
        val paramMap = StructParamMap()
        with(createDummy()){
            paramMap.encodeStruct(value)
        }
        val constructor = this::class.primaryConstructor!!
        val map = paramMap.map
        val members = this::class.memberProperties
            .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
        return constructor.callBy(constructor.parameters.associateWith {
            (members.first { mem -> mem.name == it.name }.call(this) as ReturnValue<Any?>).encode(map[it.name!!])
        })
    }
    abstract fun StructParamMap.encodeStruct(value: T)

    abstract fun ReturnScope.decode(): T

    override fun createReference(newRef: String) = Companion.createReference(this::class.createType(), newRef) as StructReturn<T>
    override fun createDummy() = createDummy(this::class.createType()) as StructReturn<T>
    inner class StructParamMap{
        val map = mutableMapOf<String, Any?>()
        operator fun <T, U: ReturnValue<T>>U.get(value: T){
            map[(type as ReturnValueType.ParserOnly).name] = value
        }
        fun getList() = map.toList()
    }
}


