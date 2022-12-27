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


data class NeoPath7<A : ReturnValue<*>, B : ReturnValue<*>, C : ReturnValue<*>, D : ReturnValue<*>, E : ReturnValue<*>, F : ReturnValue<*>, G : ReturnValue<*>, H : ReturnValue<*>, I : ReturnValue<*>, J : ReturnValue<*>, K : ReturnValue<*>, L : ReturnValue<*>, M : ReturnValue<*>>(
    val first: A,
    val firstToSecond: B,
    val second: C,
    val secondToThird: D,
    val third: E,
    val thirdToFourth: F,
    val fourth: G,
    val fourthToFifth: H,
    val fifth: I,
    val fifthToSixth: J,
    val sixth: K,
    val sixthToSeventh: L,
    val seventh: M,
    val ref: String
) : EmptyReturn()

class NeoMatchablePath7<A : Node<*>, B : Relationship<*, *, *>, C : Node<*>, D : Relationship<*, *, *>, E : Node<*>, F : Relationship<*, *, *>, G : Node<*>, H : Relationship<*, *, *>, I : Node<*>, J : Relationship<*, *, *>, K : Node<*>, L : Relationship<*, *, *>, M : Node<*>>(
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
    val fifthToSixth: RelationParamMap<J>,
    val fifthToSixthDir: PathDirection,
    val sixth: Searchable<K>,
    val sixthToSeventh: RelationParamMap<L>,
    val sixthToSeventhDir: PathDirection,
    val seventh: Searchable<M>,
    override val ref: String
) : Matchable<NeoPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>>,
    Creatable<NeoPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>> {
    override fun getCreateString() =
        "${first.getCreateString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir != PathDirection.Backwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir != PathDirection.Backwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir != PathDirection.Backwards) "->" else "-"}${fifth.getCreateString()}${if (fifthToSixthDir == PathDirection.Backwards) "<-" else "-"}${fifthToSixth.getMatchString()}${if (fifthToSixthDir != PathDirection.Backwards) "->" else "-"}${sixth.getCreateString()}${if (sixthToSeventhDir == PathDirection.Backwards) "<-" else "-"}${sixthToSeventh.getMatchString()}${if (sixthToSeventhDir != PathDirection.Backwards) "->" else "-"}${seventh.getCreateString()}"

    override fun getMatchString() =
        "(${first.getMatchString()})${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir == PathDirection.Forwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir == PathDirection.Forwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir == PathDirection.Forwards) "->" else "-"}${fifth.getCreateString()}${if (fifthToSixthDir == PathDirection.Backwards) "<-" else "-"}${fifthToSixth.getMatchString()}${if (fifthToSixthDir == PathDirection.Forwards) "->" else "-"}${sixth.getCreateString()}${if (sixthToSeventhDir == PathDirection.Backwards) "<-" else "-"}${sixthToSeventh.getMatchString()}${if (sixthToSeventhDir == PathDirection.Forwards) "->" else "-"}${seventh.getCreateString()}"

    override fun getReference() = NeoPath7(
        first.getReference(),
        firstToSecond.getReference(),
        second.getReference(),
        secondToThird.getReference(),
        third.getReference(),
        thirdToFourth.getReference(),
        fourth.getReference(),
        fourthToFifth.getReference(),
        fifth.getReference(),
        fifthToSixth.getReference(),
        sixth.getReference(),
        sixthToSeventh.getReference(),
        seventh.getReference(),
        ref
    )

}

class NoDirOpenPath7<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>>(
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
    val fifthToSixth: RelationParamMap<J>,
    val fifthToSixthDir: PathDirection,
    val sixth: Searchable<K>,
    val sixthToSeventh: RelationParamMap<L>,
    val ref: String
)

class ForwardOpenPath7<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>>(
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
    val fifthToSixth: RelationParamMap<J>,
    val fifthToSixthDir: PathDirection,
    val sixth: Searchable<K>,
    val sixthToSeventh: RelationParamMap<L>,
    val ref: String
)

class BackwardsOpenPath7<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>>(
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
    val fifthToSixth: RelationParamMap<J>,
    val fifthToSixthDir: PathDirection,
    val sixth: Searchable<K>,
    val sixthToSeventh: RelationParamMap<L>,
    val ref: String
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`o-→`(relation: RelationParamMap<L>) =
    ForwardOpenPath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`o-→`(relation: KFunction<L>) =
    ForwardOpenPath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        relation {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`←-o`(relation: RelationParamMap<L>) =
    BackwardsOpenPath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`←-o`(relation: KFunction<L>) =
    BackwardsOpenPath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        relation {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`-o-`(
    relation: RelationParamMap<L>
) = NoDirOpenPath7(
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
    fourthToFifthDir,
    fifth,
    fifthToSixth,
    fifthToSixthDir,
    sixth,
    relation,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>> NeoMatchablePath6<A, B, C, D, E, F, G, H, I, J, K>.`-o-`(
    relation: KFunction<L>
) = NoDirOpenPath7(
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
    fourthToFifthDir,
    fifth,
    fifthToSixth,
    fifthToSixthDir,
    sixth,
    relation {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>> BackwardsOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`←-o`(node: Searchable<M>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.Backwards,
        node,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>> BackwardsOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`←-o`(node: KFunction<M>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.Backwards,
        node {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<M, K, *>,
        M : Node<*>> BackwardsOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`←-o`(node: M) = NeoMatchablePath7(
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
    fourthToFifthDir,
    fifth,
    fifthToSixth,
    fifthToSixthDir,
    sixth,
    sixthToSeventh,
    PathDirection.Backwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>> ForwardOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`o-→`(node: Searchable<M>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.Forwards,
        node,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>> ForwardOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`o-→`(node: KFunction<M>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.Forwards,
        node {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : DirectionalRelationship<K, M, *>,
        M : Node<*>> ForwardOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L, M>.`o-→`(node: M) = NeoMatchablePath7(
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
    fourthToFifthDir,
    fifth,
    fifthToSixth,
    fifthToSixthDir,
    sixth,
    sixthToSeventh,
    PathDirection.Forwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>> NoDirOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L>.`-o-`(node: Searchable<K>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.None,
        node,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>> NoDirOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L>.`-o-`(node: KFunction<K>) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.None,
        node {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : Relationship<*, *, *>,
        K : Node<*>, L : NonDirectionalRelationship<K, *>> NoDirOpenPath7<A, B, C, D, E, F, G, H, I, J, K, L>.`-o-`(node: K) =
    NeoMatchablePath7(
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
        fourthToFifthDir,
        fifth,
        fifthToSixth,
        fifthToSixthDir,
        sixth,
        sixthToSeventh,
        PathDirection.None,
        NodeReference(node),
        ref
    )

