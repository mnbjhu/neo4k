package uk.gibby.neo4k.core

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val code: String,
    val description: String,
    val position: Position,
    val severity: String,
    val title: String
)