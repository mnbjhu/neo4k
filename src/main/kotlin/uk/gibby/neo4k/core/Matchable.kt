package uk.gibby.neo4k.core


interface Matchable<out T>: Referencable<T>{
    fun getMatchString(): String
}