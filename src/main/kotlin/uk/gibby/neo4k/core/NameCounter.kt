package uk.gibby.neo4k.core

object NameCounter {
    private var count = 0
    fun next() = "obj${count++}"
}
