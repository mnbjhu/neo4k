package uk.gibby.neo4k.clauses

sealed class Clause {
    abstract fun getString(): String
}