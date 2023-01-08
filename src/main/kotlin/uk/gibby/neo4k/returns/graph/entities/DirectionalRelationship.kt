package uk.gibby.neo4k.returns.graph.entities



abstract class DirectionalRelationship<out A: Node<*>, out B: Node<*>, T>: Relationship<A, B, T>()


abstract class Relationship<out A: Node<*>, out B: Node<*>, T>: Entity<T>()

abstract class NonDirectionalRelationship<out A: Node<*>, T>: Relationship<A, A, T>()