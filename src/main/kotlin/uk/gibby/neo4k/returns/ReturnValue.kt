package uk.gibby.neo4k.returns


import kotlinx.serialization.KSerializer
import uk.gibby.neo4k.returns.generic.ArrayReturn
import uk.gibby.neo4k.returns.generic.Nullable
import uk.gibby.neo4k.returns.generic.StructReturn
import uk.gibby.neo4k.returns.graph.entities.Entity
import uk.gibby.neo4k.returns.primitives.PrimitiveReturn
import uk.gibby.neo4k.returns.util.Box
import uk.gibby.neo4k.returns.util.ReturnValueType
import kotlin.reflect.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.primaryConstructor


/**
 * Return value
 *
 * A value which can be returned from a graph query: [uk.gibby.neo4k.core.Graph].
 * i.e. RETURN myValue
 * @param T Return value type.
 */
abstract class ReturnValue<T>{
    internal abstract val serializer: KSerializer<T>
    /**
     * Type
     *
     * Determines whether the instance is:
     * - A reference ([ReturnValueType.Reference])
     * - A structured representation ([ReturnValueType.Instance])
     * - Only to be used for its parser method ([ReturnValueType.ParserOnly])
     */
    internal var type: ReturnValueType = ReturnValueType.Instance

    /**
     * Get string
     *
     * The string representation of the return value. When the instance is
     * - A reference [ReturnValueType.Reference.ref]
     * - A structured representation [getStructuredString]
     *
     * E.g
     * - obj1.myVar
     * - {myString:'Some String', myInt: 123}
     *
     * @throws Exception If the ReturnValue is a parser only
     */
    internal fun getString() = when(type){
        is ReturnValueType.ParserOnly -> throw Exception("Cannot call getString, instance is parser only")
        is ReturnValueType.Reference -> (type as ReturnValueType.Reference).ref
        is ReturnValueType.Instance -> getStructuredString()
    }

    /**
     * Get structured string
     *
     * E.g.
     *
     * - [uk.gibby.neo4k.returns.primitives.LongReturn] -> 23
     * - [uk.gibby.neo4k.returns.primitives.StringReturn] -> 'Some String'
     * - [StructReturn] -> {myNumVar: 1, myStringVar: 'Some Other String'}
     *
     * @return The structured representation of the instance
     */
    internal abstract fun getStructuredString(): String

    /**
     * Parse
     *
     * Parses the response data as [T]
     *
     * @param value Response data
     * @return The decoded response
     */
    internal abstract fun parse(value: Any?): T

    /**
     * Encode
     *
     * Creates a structured representation ([ReturnValue]) from a decoded value ([T])
     *
     * @param value The value to encode
     * @return Structured representation of [value]
     */
    abstract fun encode(value: T): ReturnValue<T>
    override fun equals(other: Any?): Boolean {
        return if(other is ReturnValue<*>) type == other.type
        else false
    }

    internal abstract fun createReference(newRef: String): ReturnValue<T>
    internal abstract fun createDummy(): ReturnValue<T>
    companion object{
        private val dummyCache = mutableMapOf<String, Any>()
        internal fun <T, U: ReturnValue<T>, A: ArrayReturn<T, U>>createDummyArray(type: KType): Any{
            return ArrayReturn(Box.WithoutValue, createDummy(type) as U)
                .apply { this.type = ReturnValueType.ParserOnly }
        }
        internal fun <T, U: ReturnValue<T>, A: ArrayReturn<T, U>>createReferenceArray(type: KType, ref: String): Any{
            return ArrayReturn(Box.WithoutValue, createDummy(type) as U)
                .apply { this.type = ReturnValueType.Reference(ref) }
        }
        internal fun createDummy(type: KType): Any{
            val cached = dummyCache[type.toString()]
            if(cached != null) return cached
            return when{
                type.isSubtypeOf(ArrayReturn::class.createType(listOf(KTypeProjection.STAR, KTypeProjection.STAR))) -> {
                    createDummyArray<Any, ReturnValue<Any>, ArrayReturn<Any, ReturnValue<Any>>>(type.arguments[1].type!!)
                }
                type.isSubtypeOf(StructReturn::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    val params = constructor.parameters.associateWith {
                        createDummy(it.type)
                    }
                    (constructor.callBy(params) as ReturnValue<*>).apply { this.type = ReturnValueType.ParserOnly }
                }
                type.isSubtypeOf(Entity::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    val params = constructor.parameters.associateWith {
                        if(!it.type.isSubtypeOf(NotNull::class.createType(listOf(KTypeProjection.STAR))) &&
                            !it.type.isSubtypeOf(
                                Nullable::class.createType(listOf(
                                KTypeProjection.STAR,
                                KTypeProjection(KVariance.OUT, NotNull::class.createType(listOf(KTypeProjection.STAR)))))))
                            throw Exception("Attributes can only be return_types.NotNull or return_types.Nullable<return_types.NotNull>")
                        createReference(it.type, it.name!!)
                    }
                    (constructor.callBy(params) as ReturnValue<*>).apply { this.type = ReturnValueType.ParserOnly }
                }
                type.isSubtypeOf(Nullable::class.createType(listOf(KTypeProjection.STAR, KTypeProjection.STAR)), ) -> {
                    Nullable<Any, NotNull<Any>>(Box.WithoutValue, createDummy(type.arguments[1].type!!) as NotNull<Any>)
                        .apply { this.type = ReturnValueType.ParserOnly }
                }
                type.isSubtypeOf(PrimitiveReturn::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    (constructor.call(null) as ReturnValue<*>).apply { this.type = ReturnValueType.ParserOnly }
                }
                else -> throw Exception("Couldn't create dummy for type: $type")
            }.also { dummyCache[type.toString()] = it }
        }
        internal fun createReference(type: KType, ref: String): Any{
            return when{
                type.isSubtypeOf(ArrayReturn::class.createType(listOf(KTypeProjection.STAR, KTypeProjection.STAR))) -> {
                    createReferenceArray<Any, ReturnValue<Any>, ArrayReturn<Any, ReturnValue<Any>>>(type.arguments[1].type!!, ref)
                }
                type.isSubtypeOf(StructReturn::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    var index = 0
                    val params = constructor.parameters.associateWith {
                        if(!it.type.isSubtypeOf(NotNull::class.createType(listOf(KTypeProjection.STAR))) &&
                            !it.type.isSubtypeOf(Nullable::class.createType(listOf(KTypeProjection.STAR, KTypeProjection(KVariance.OUT, NotNull::class.createType(listOf(KTypeProjection.STAR)))))))
                            throw Exception("Attributes can only be return_types.NotNull or return_types.Nullable<return_types.NotNull>")
                        createReference(it.type, ref + "[${index++}]")
                    }
                    (constructor.callBy(params) as ReturnValue<*>).apply { this.type = ReturnValueType.Reference(ref) }
                }
                type.isSubtypeOf(Entity::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    val params = constructor.parameters.associateWith {
                        if(!it.type.isSubtypeOf(NotNull::class.createType(listOf(KTypeProjection.STAR))) &&
                            !it.type.isSubtypeOf(Nullable::class.createType(listOf(KTypeProjection.STAR, KTypeProjection(KVariance.OUT, NotNull::class.createType(listOf(KTypeProjection.STAR)))))))
                            throw Exception("Attributes can only be return_types.NotNull or return_types.Nullable<return_types.NotNull>")
                        createReference(it.type, ref + "." + it.name)
                    }
                    (constructor.callBy(params) as ReturnValue<*>).apply { this.type = ReturnValueType.Reference(ref) }
                }
                type.isSubtypeOf(Nullable::class.createType(listOf(KTypeProjection.STAR, KTypeProjection.STAR)), ) -> {
                    Nullable(Box.WithoutValue, createDummy(type.arguments[1].type!!) as NotNull<Any>)
                        .apply { this.type = ReturnValueType.Reference(ref) }
                }
                type.isSubtypeOf(PrimitiveReturn::class.createType(listOf(KTypeProjection.STAR))) -> {
                    val constructor = (type.classifier as KClass<*>).primaryConstructor!!
                    (constructor.call(null) as ReturnValue<*>).apply { this.type = ReturnValueType.Reference(ref) }
                }
                else -> throw Exception()
            }
        }

        internal fun <T, U: ReturnValue<T>>createDummy(type: KFunction<U>): U{
            val cached = dummyCache[type.toString()]
            return if(cached == null){
                (createDummy(type.returnType) as U).also { dummyCache[type.toString()] = it }
            } else (cached as U)
        }

        internal fun <T, U: ReturnValue<T>>createReference(type: KFunction<U>, ref: String): U{

            return createDummy(type).createReference(ref) as U
        }


        internal fun <T, U: ReturnValue<T>>createInstance(type: KFunction<U>, value: T): U{
            val dummy = createDummy(type)
            return dummy.encode(value) as U
        }
        internal fun <T, U: ReturnValue<T>>createInstance(type: KType, value: T): U{
            val dummy = createDummy(type) as U
            return dummy.encode(value) as U
        }

    }

}


