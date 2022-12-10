package uk.gibby.neo4k.core

interface Searchable<out T>{
    val ref: String
    fun getReference(): T
    fun getSearchString(): String
}