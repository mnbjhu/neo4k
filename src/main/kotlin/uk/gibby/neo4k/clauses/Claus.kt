package uk.gibby.neo4k.clauses

sealed class Claus {
    abstract fun getString(): String
}