package uk.gibby.neo4k.core

import uk.gibby.neo4k.returns.ReturnValue
import kotlin.reflect.KClass
import kotlin.reflect.KType

interface Label<T>: NodeType<T>
interface NodeType<T>

abstract class LabelFactory<T, U: Label<T>>(val type: KType): Searchable<U>{
    override val ref: String = NameCounter.next()
    abstract fun getLabel(): U
    override fun getReference(): U = getLabel()
    override fun getSearchString(): String {
        return "($ref:${(type.classifier as KClass<*>).simpleName})"
    }
}
