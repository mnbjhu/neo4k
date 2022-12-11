package uk.gibby.neo4k.core

interface Creatable<out T>: Referencable<T>{
    fun getCreateString(): String
}