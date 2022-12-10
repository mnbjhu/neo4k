package uk.gibby.neo4k.returns.util

sealed class Box<out T>{
    class WithValue<T>(val value: T): Box<T>()
    object WithoutValue: Box<Nothing>()
}