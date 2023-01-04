package uk.gibby.neo4k.core

import kotlinx.serialization.Serializable

@Serializable
data class NeoError(
    val code: String,
    val message: String
)