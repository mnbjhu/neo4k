package uk.gibby.neo4k.core

import kotlinx.serialization.Serializable

@Serializable
data class Position(
    val column: Int,
    val line: Int,
    val offset: Int
)