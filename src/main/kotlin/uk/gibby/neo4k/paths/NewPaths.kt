package uk.gibby.neo4k.paths

import uk.gibby.neo4k.core.*
import uk.gibby.neo4k.returns.ReturnValue
import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.NonDirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.Relationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

/**
 * Node -o- Relation = NoDirOpenPath2
 * Node o-> Relation = ForwardDirOpenPath2
 * Node <-o Relation = BackwardsDirOpenPath2
 *
 */


infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>NodeParamMap<A>
        .`-o-`(relation: RelationParamMap<B>): NoDirOpenPath2<A, B> {
    return NoDirOpenPath2(this, relation)
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>KFunction<A>
        .`-o-`(relation: RelationParamMap<B>): NoDirOpenPath2<A, B>{
    return NoDirOpenPath2(this{}, relation)
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>NodeParamMap<A>
        .`-o-`(relation: KFunction<B>): NoDirOpenPath2<A, B> {
    return NoDirOpenPath2(this, relation{})
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>KFunction<A>
        .`-o-`(relation: KFunction<B>): NoDirOpenPath2<A, B>{
    return NoDirOpenPath2(this{}, relation{})
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>A
        .`-o-`(relation: KFunction<B>): NoDirOpenPath2<A, B> {
    return NoDirOpenPath2(NodeReference(this), relation{})
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>A
        .`-o-`(relation: RelationParamMap<B>): NoDirOpenPath2<A, B>{
    return NoDirOpenPath2(NodeReference(this), relation)
}



infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>NodeParamMap<C>
        .`←-o`(relation: RelationParamMap<B>): BackwardsOpenPath2<A, B, C> {
    return BackwardsOpenPath2(this, relation)
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>KFunction<C>
        .`←-o`(relation: RelationParamMap<B>):BackwardsOpenPath2<A, B, C>{
    return BackwardsOpenPath2(this{}, relation)
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>NodeParamMap<C>
        .`←-o`(relation: KFunction<B>): BackwardsOpenPath2<A, B, C> {
    return BackwardsOpenPath2(this, relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>KFunction<C>
        .`←-o`(relation: KFunction<B>):BackwardsOpenPath2<A, B, C>{
    return BackwardsOpenPath2(this{}, relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>C
        .`←-o`(relation: KFunction<B>): BackwardsOpenPath2<A, B, C> {
    return BackwardsOpenPath2(NodeReference(this), relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>C
        .`←-o`(relation: RelationParamMap<B>): BackwardsOpenPath2<A, B, C>{
    return BackwardsOpenPath2(NodeReference(this), relation)
}


infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>NodeParamMap<A>
        .`o-→`(relation: RelationParamMap<B>): ForwardOpenPath2<A, B, C> {
    return ForwardOpenPath2(this, relation)
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>KFunction<A>
        .`o-→`(relation: RelationParamMap<B>):ForwardOpenPath2<A, B, C>{
    return ForwardOpenPath2(this{}, relation)
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>NodeParamMap<A>
        .`o-→`(relation: KFunction<B>): ForwardOpenPath2<A, B, C> {
    return ForwardOpenPath2(this, relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>KFunction<A>
        .`o-→`(relation: KFunction<B>):ForwardOpenPath2<A, B, C>{
    return ForwardOpenPath2(this{}, relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>A
        .`o-→`(relation: KFunction<B>): ForwardOpenPath2<A, B, C> {
    return ForwardOpenPath2(NodeReference(this), relation{})
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>A
        .`o-→`(relation: RelationParamMap<B>): ForwardOpenPath2<A, B, C>{
    return ForwardOpenPath2(NodeReference(this), relation)
}




infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>ForwardOpenPath2<A, B, C>.`o-→`(node: KFunction<C>): NeoMatchablePath2<A, B, C>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Forwards,  node{}, NameCounter.next())
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>ForwardOpenPath2<A, B, C>.`o-→`(node: NodeParamMap<C>): NeoMatchablePath2<A, B, C>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Forwards,  node, NameCounter.next())
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>ForwardOpenPath2<A, B, C>.`o-→`(node: C): NeoMatchablePath2<A, B, C>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Forwards,  NodeReference(node), NameCounter.next())
}


infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>NoDirOpenPath2<A, B>.`-o-`(node: KFunction<A>): NeoMatchablePath2<A, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.None,  node{}, NameCounter.next())
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>NoDirOpenPath2<A, B>.`-o-`(node: NodeParamMap<A>): NeoMatchablePath2<A, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.None,  node, NameCounter.next())
}
infix fun <A: Node<*>, B: NonDirectionalRelationship<A, *>>NoDirOpenPath2<A, B>.`-o-`(node: A): NeoMatchablePath2<A, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.None,  NodeReference(node), NameCounter.next())
}


infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>BackwardsOpenPath2<A, B, C>.`←-o`(node: KFunction<A>): NeoMatchablePath2<C, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Backwards,  node{}, NameCounter.next())
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>BackwardsOpenPath2<A, B, C>.`←-o`(node: NodeParamMap<A>): NeoMatchablePath2<C, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Backwards,  node, NameCounter.next())
}
infix fun <A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>BackwardsOpenPath2<A, B, C>.`←-o`(node: A): NeoMatchablePath2<C, B, A>{
    return NeoMatchablePath2(first, firstToSecond, PathDirection.Backwards,  NodeReference(node), NameCounter.next())
}

//`←-o`

data class NeoPath2<A: ReturnValue<*>, B: ReturnValue<*>, C: ReturnValue<*>>(val first: A, val firstToSecond: B, val second: C, val ref: String): EmptyReturn()

class NoDirOpenPath2<A: Node<*>, B: NonDirectionalRelationship<A, *>>(val first: Searchable<A>, val firstToSecond: RelationParamMap<B>)

class ForwardOpenPath2<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>(val first: Searchable<A>, val firstToSecond: RelationParamMap<B>)

class BackwardsOpenPath2<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>>(val first: Searchable<C>, val firstToSecond: RelationParamMap<B>)

class NeoMatchablePath2<A: Node<*>, B: Relationship<*, *, *>, C: Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    override val ref: String
): Matchable<NeoPath2<A, B, C>>, Creatable<NeoPath2<A, B, C>> {
    override fun getCreateString(): String {
        return "${first.getSearchString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}${second.getSearchString()}"
    }

    override fun getReference(): NeoPath2<A, B, C> {
        return NeoPath2(first.getReference(), firstToSecond.getReference(), second.getReference(), ref)
    }

    override fun getMatchString(): String {
        return "${first.getSearchString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}${second.getSearchString()}"
    }
}

enum class PathDirection{
    Forwards,
    Backwards,
    None
}