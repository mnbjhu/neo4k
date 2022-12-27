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


data class NeoPath5<A : ReturnValue<*>, B : ReturnValue<*>, C : ReturnValue<*>, D : ReturnValue<*>, E : ReturnValue<*>, F : ReturnValue<*>, G : ReturnValue<*>, H : ReturnValue<*>, I : ReturnValue<*>>(
    val first: A,
    val firstToSecond: B,
    val second: C,
    val secondToThird: D,
    val third: E,
    val thirdToFourth: F,
    val fourth: G,
    val fourthToFifth: H,
    val fifth: I,
    val ref: String
) : EmptyReturn()

class NeoMatchablePath5<A : Node<*>, B : Relationship<*, *, *>, C : Node<*>, D : Relationship<*, *, *>, E : Node<*>, F : Relationship<*, *, *>, G : Node<*>, H : Relationship<*, *, *>, I : Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val thirdToFourthDir: PathDirection,
    val fourth: Searchable<G>,
    val fourthToFifth: RelationParamMap<H>,
    val fourthToFifthDir: PathDirection,
    val fifth: Searchable<I>,
    override val ref: String
) : Matchable<NeoPath5<A, B, C, D, E, F, G, H, I>>, Creatable<NeoPath5<A, B, C, D, E, F, G, H, I>> {
    override fun getCreateString() =
        "${first.getCreateString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir != PathDirection.Backwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir != PathDirection.Backwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir != PathDirection.Backwards) "->" else "-"}${fifth.getCreateString()}"

    override fun getMatchString() =
        "(${first.getMatchString()})${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir == PathDirection.Forwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir == PathDirection.Forwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir == PathDirection.Forwards) "->" else "-"}${fifth.getCreateString()}"

    override fun getReference() = NeoPath5(
        first.getReference(),
        firstToSecond.getReference(),
        second.getReference(),
        secondToThird.getReference(),
        third.getReference(),
        thirdToFourth.getReference(),
        fourth.getReference(),
        fourthToFifth.getReference(),
        fifth.getReference(),
        ref
    )

}

class NoDirOpenPath5<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val thirdToFourthDir: PathDirection,
    val fourth: Searchable<G>,
    val fourthToFifth: RelationParamMap<H>,
    val ref: String
)

class ForwardOpenPath5<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val thirdToFourthDir: PathDirection,
    val fourth: Searchable<G>,
    val fourthToFifth: RelationParamMap<H>,
    val ref: String
)

class BackwardsOpenPath5<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val thirdToFourthDir: PathDirection,
    val fourth: Searchable<G>,
    val fourthToFifth: RelationParamMap<H>,
    val ref: String
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>> NeoMatchablePath4<A, B, C, D, E, F, G>.`o-→`(relation: RelationParamMap<H>) = ForwardOpenPath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    relation,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>> NeoMatchablePath4<A, B, C, D, E, F, G>.`o-→`(relation: KFunction<H>) = ForwardOpenPath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    relation {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>> NeoMatchablePath4<A, B, C, D, E, F, G>.`←-o`(relation: RelationParamMap<H>) = BackwardsOpenPath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    relation,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>> NeoMatchablePath4<A, B, C, D, E, F, G>.`←-o`(relation: KFunction<H>) = BackwardsOpenPath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    relation {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>> NeoMatchablePath4<A, B, C, D, E, F, G>.`-o-`(relation: RelationParamMap<H>) =
    NoDirOpenPath5(
        first,
        firstToSecond,
        firstToSecondDir,
        second,
        secondToThird,
        secondToThirdDir,
        third,
        thirdToFourth,
        thirdToFourthDir,
        fourth,
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>> NeoMatchablePath4<A, B, C, D, E, F, G>.`-o-`(relation: KFunction<H>) =
    NoDirOpenPath5(
        first,
        firstToSecond,
        firstToSecondDir,
        second,
        secondToThird,
        secondToThirdDir,
        third,
        thirdToFourth,
        thirdToFourthDir,
        fourth,
        relation {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>> BackwardsOpenPath5<A, B, C, D, E, F, G, H, I>.`←-o`(node: Searchable<I>) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Backwards,
    node,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>> BackwardsOpenPath5<A, B, C, D, E, F, G, H, I>.`←-o`(node: KFunction<I>) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Backwards,
    node {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<I, G, *>,
        I : Node<*>> BackwardsOpenPath5<A, B, C, D, E, F, G, H, I>.`←-o`(node: I) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Backwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>> ForwardOpenPath5<A, B, C, D, E, F, G, H, I>.`o-→`(node: Searchable<I>) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Forwards,
    node,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>> ForwardOpenPath5<A, B, C, D, E, F, G, H, I>.`o-→`(node: KFunction<I>) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Forwards,
    node {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : DirectionalRelationship<G, I, *>,
        I : Node<*>> ForwardOpenPath5<A, B, C, D, E, F, G, H, I>.`o-→`(node: I) = NeoMatchablePath5(
    first,
    firstToSecond,
    firstToSecondDir,
    second,
    secondToThird,
    secondToThirdDir,
    third,
    thirdToFourth,
    thirdToFourthDir,
    fourth,
    fourthToFifth,
    PathDirection.Forwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>> NoDirOpenPath5<A, B, C, D, E, F, G, H>.`-o-`(node: Searchable<G>) =
    NeoMatchablePath5(
        first,
        firstToSecond,
        firstToSecondDir,
        second,
        secondToThird,
        secondToThirdDir,
        third,
        thirdToFourth,
        thirdToFourthDir,
        fourth,
        fourthToFifth,
        PathDirection.None,
        node,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>> NoDirOpenPath5<A, B, C, D, E, F, G, H>.`-o-`(node: KFunction<G>) =
    NeoMatchablePath5(
        first,
        firstToSecond,
        firstToSecondDir,
        second,
        secondToThird,
        secondToThirdDir,
        third,
        thirdToFourth,
        thirdToFourthDir,
        fourth,
        fourthToFifth,
        PathDirection.None,
        node {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : NonDirectionalRelationship<G, *>> NoDirOpenPath5<A, B, C, D, E, F, G, H>.`-o-`(node: G) =
    NeoMatchablePath5(
        first,
        firstToSecond,
        firstToSecondDir,
        second,
        secondToThird,
        secondToThirdDir,
        third,
        thirdToFourth,
        thirdToFourthDir,
        fourth,
        fourthToFifth,
        PathDirection.None,
        NodeReference(node),
        ref
    )

