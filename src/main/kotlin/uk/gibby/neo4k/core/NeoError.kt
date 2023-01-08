package uk.gibby.neo4k.core

import kotlinx.serialization.Serializable

@Serializable
data class NeoError(
    val code: String,
    val message: String
){
    fun getError() = Exception("Neo4j responded with - Code: '$code', message: '$message'")
}