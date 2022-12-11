package uk.gibby.neo4k.core

interface Searchable<out T>: Matchable<T>, Creatable<T>{
    fun getSearchString(): String
    override fun getCreateString(): String {
        return getSearchString()
    }
    override fun getMatchString(): String {
        return getSearchString()
    }
}
interface Referencable<out T>{
    val ref: String
    fun getReference(): T
}