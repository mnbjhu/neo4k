package uk.gibby.neo4k.returns.graph.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import uk.gibby.neo4k.returns.NotNull
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.util.ReturnScope
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

sealed class Entity<T>: NotNull<T>(){
    val elements by lazy { this::class.memberProperties
        .filter { it.returnType.isSubtypeOf(ReturnValue::class.createType(listOf(KTypeProjection.STAR))) }
        .map { it.name to (it.call(this) as ReturnValue<*>) }
    }
    val indexMap by lazy{ elements.mapIndexed { index, pair -> pair.second to index }.toMap() }
    override val serializer: KSerializer<T> by lazy { object : KSerializer<T>{
        override val descriptor = buildClassSerialDescriptor(this@Entity::class.simpleName!!){
            elements.forEach {
                element(it.first, it.second.serializer.descriptor)
            }
        }

        override fun deserialize(decoder: Decoder): T {
            val composite = decoder.beginStructure(descriptor)
            val map = mutableMapOf<String, Any?>()
            while (true){
                when(val index = composite.decodeElementIndex(descriptor)){
                    DECODE_DONE -> break
                    else -> {
                        val element = elements[index]
                        map[element.first] = composite.decodeSerializableElement(descriptor, index, element.second.serializer)
                    }
                }
            }
            composite.endStructure(descriptor)
                return ReturnScope(map).decode()
        }

        override fun serialize(encoder: Encoder, value: T) {
            // TODO:
        }

    }}
    override fun getStructuredString(): String {
        throw Exception("Only references to entities should be created")
    }

    override fun encode(value: T): ReturnValue<T> {
        throw Exception("Only references to entities should be created")
    }

    override fun createReference(newRef: String): Entity<T> {
        return Companion.createReference(this::class.createType(), newRef) as Entity<T>
    }
    abstract fun ReturnScope.decode(): T
    override fun createDummy(): Entity<T> = createDummy(this::class.createType()) as Entity<T>
}