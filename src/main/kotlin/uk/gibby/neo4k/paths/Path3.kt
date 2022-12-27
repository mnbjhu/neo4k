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

data class NeoPath3<A : ReturnValue<*>, B : ReturnValue<*>, C : ReturnValue<*>, D : ReturnValue<*>, E : ReturnValue<*>>(
    val first: A,
    val firstToSecond: B,
    val second: C,
    val secondToThird: D,
    val third: E,
    val ref: String
) : EmptyReturn()

class NoDirOpenPath3<
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        >(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val ref: String
)

class ForwardOpenPath3<
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        >(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val ref: String
)

class BackwardsOpenPath3<
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        >(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val ref: String
)

class NeoMatchablePath3<
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : Relationship<*, *, *>,
        E : Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    override val ref: String
) : Matchable<NeoPath3<A, B, C, D, E>>, Creatable<NeoPath3<A, B, C, D, E>> {
    override fun getCreateString(): String {
        return "${first.getSearchString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}${second.getSearchString()}" +
                "${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir != PathDirection.Backwards) "->" else "-"}${third.getSearchString()}"
    }

    override fun getReference(): NeoPath3<A, B, C, D, E> {
        return NeoPath3(
            first.getReference(),
            firstToSecond.getReference(),
            second.getReference(),
            secondToThird.getReference(),
            third.getReference(),
            ref
        )
    }

    override fun getMatchString(): String {
        return "${first.getSearchString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}${second.getSearchString()}" +
                "${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir == PathDirection.Forwards) "->" else "-"}${third.getSearchString()}"
    }
}


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        > NeoMatchablePath2<A, B, C>.`o-→`(relation: RelationParamMap<D>) =
    ForwardOpenPath3(first, firstToSecond, firstToSecondDir, second, relation, ref)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        > NeoMatchablePath2<A, B, C>.`o-→`(relation: KFunction<D>) =
    ForwardOpenPath3(first, firstToSecond, firstToSecondDir, second, relation { }, ref)


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        > NeoMatchablePath2<A, B, C>.`←-o`(relation: RelationParamMap<D>) =
    BackwardsOpenPath3(first, firstToSecond, firstToSecondDir, second, relation, ref)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        > NeoMatchablePath2<A, B, C>.`←-o`(relation: KFunction<D>) =
    BackwardsOpenPath3(first, firstToSecond, firstToSecondDir, second, relation { }, ref)


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        > NeoMatchablePath2<A, B, C>.`-o-`(relation: KFunction<D>) =
    NoDirOpenPath3(first, firstToSecond, firstToSecondDir, second, relation { }, ref)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        > NeoMatchablePath2<A, B, C>.`-o-`(relation: RelationParamMap<D>) =
    NoDirOpenPath3(first, firstToSecond, firstToSecondDir, second, relation, ref)


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        > ForwardOpenPath3<A, B, C, D, E>.`o-→`(node: E) = NeoMatchablePath3(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    PathDirection.Forwards,
    NodeReference(node),
    ref
)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        > ForwardOpenPath3<A, B, C, D, E>.`o-→`(node: KFunction<E>) = NeoMatchablePath3(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    PathDirection.Forwards,
    node {},
    ref
)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<C, E, *>,
        E : Node<*>
        > ForwardOpenPath3<A, B, C, D, E>.`o-→`(node: NodeParamMap<E>) =
    NeoMatchablePath3(first, firstToSecond, firstToSecondDir, second, secondToThird, PathDirection.Forwards, node, ref)


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        > BackwardsOpenPath3<A, B, C, D, E>.`←-o`(node: E) = NeoMatchablePath3(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    PathDirection.Backwards,
    NodeReference(node),
    ref
)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        > BackwardsOpenPath3<A, B, C, D, E>.`←-o`(node: KFunction<E>) = NeoMatchablePath3(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    PathDirection.Backwards,
    node {},
    ref
)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : DirectionalRelationship<E, C, *>,
        E : Node<*>
        > BackwardsOpenPath3<A, B, C, D, E>.`←-o`(node: NodeParamMap<E>) =
    NeoMatchablePath3(first, firstToSecond, firstToSecondDir, second, secondToThird, PathDirection.Backwards, node, ref)


infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        > NoDirOpenPath3<A, B, C, D>.`-o-`(node: C) = NeoMatchablePath3(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    PathDirection.None,
    NodeReference(node),
    ref
)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        > NoDirOpenPath3<A, B, C, D>.`-o-`(node: KFunction<C>) =
    NeoMatchablePath3(first, firstToSecond, firstToSecondDir, second, secondToThird, PathDirection.None, node {}, ref)

infix fun <
        A : Node<*>,
        B : Relationship<*, *, *>,
        C : Node<*>,
        D : NonDirectionalRelationship<C, *>
        > NoDirOpenPath3<A, B, C, D>.`-o-`(node: NodeParamMap<C>) =
    NeoMatchablePath3(first, firstToSecond, firstToSecondDir, second, secondToThird, PathDirection.None, node, ref)

