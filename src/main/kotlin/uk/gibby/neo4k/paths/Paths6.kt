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


data class NeoPath6<A : ReturnValue<*>, B : ReturnValue<*>, C : ReturnValue<*>, D : ReturnValue<*>, E : ReturnValue<*>, F : ReturnValue<*>, G : ReturnValue<*>, H : ReturnValue<*>, I : ReturnValue<*>, J : ReturnValue<*>, K : ReturnValue<*>>(
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
    val ref: String
) : EmptyReturn()

class NeoMatchablePath6<A : Node<*>, B : Relationship<*, *, *>, C : Node<*>, D : Relationship<*, *, *>, E : Node<*>, F : Relationship<*, *, *>, G : Node<*>, H : Relationship<*, *, *>, I : Node<*>, J : Relationship<*, *, *>, K : Node<*>>(
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
    override val ref: String
) : Matchable<NeoPath6<A, B, C, D, E, F, G, H, I, J, K>>, Creatable<NeoPath6<A, B, C, D, E, F, G, H, I, J, K>> {
    override fun getCreateString() =
        "${first.getCreateString()}${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir != PathDirection.Backwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir != PathDirection.Backwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir != PathDirection.Backwards) "->" else "-"}${fifth.getCreateString()}${if (fifthToSixthDir == PathDirection.Backwards) "<-" else "-"}${fifthToSixth.getMatchString()}${if (fifthToSixthDir != PathDirection.Backwards) "->" else "-"}${sixth.getCreateString()}"

    override fun getMatchString() =
        "(${first.getMatchString()})${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}${firstToSecond.getMatchString()}${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}${second.getCreateString()}${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}${secondToThird.getMatchString()}${if (secondToThirdDir == PathDirection.Forwards) "->" else "-"}${third.getCreateString()}${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}${thirdToFourth.getMatchString()}${if (thirdToFourthDir == PathDirection.Forwards) "->" else "-"}${fourth.getCreateString()}${if (fourthToFifthDir == PathDirection.Backwards) "<-" else "-"}${fourthToFifth.getMatchString()}${if (fourthToFifthDir == PathDirection.Forwards) "->" else "-"}${fifth.getCreateString()}${if (fifthToSixthDir == PathDirection.Backwards) "<-" else "-"}${fifthToSixth.getMatchString()}${if (fifthToSixthDir == PathDirection.Forwards) "->" else "-"}${sixth.getCreateString()}"

    override fun getReference() = NeoPath6(
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
        ref
    )

}

class NoDirOpenPath6<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>>(
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
    val ref: String
)

class ForwardOpenPath6<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>>(
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
    val ref: String
)

class BackwardsOpenPath6<A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>>(
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
    val ref: String
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`o-→`(relation: RelationParamMap<J>) =
    ForwardOpenPath6(
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
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`o-→`(relation: KFunction<J>) = ForwardOpenPath6(
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
    relation {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`←-o`(relation: RelationParamMap<J>) =
    BackwardsOpenPath6(
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
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`←-o`(relation: KFunction<J>) = BackwardsOpenPath6(
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
    relation {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`-o-`(relation: RelationParamMap<J>) =
    NoDirOpenPath6(
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
        relation,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>> NeoMatchablePath5<A, B, C, D, E, F, G, H, I>.`-o-`(relation: KFunction<J>) =
    NoDirOpenPath6(
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
        relation {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>> BackwardsOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`←-o`(node: Searchable<K>) = NeoMatchablePath6(
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
    PathDirection.Backwards,
    node,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>> BackwardsOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`←-o`(node: KFunction<K>) = NeoMatchablePath6(
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
    PathDirection.Backwards,
    node {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<K, I, *>,
        K : Node<*>> BackwardsOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`←-o`(node: K) = NeoMatchablePath6(
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
    PathDirection.Backwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>> ForwardOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`o-→`(node: Searchable<K>) = NeoMatchablePath6(
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
    PathDirection.Forwards,
    node,
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>> ForwardOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`o-→`(node: KFunction<K>) = NeoMatchablePath6(
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
    PathDirection.Forwards,
    node {},
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : DirectionalRelationship<I, K, *>,
        K : Node<*>> ForwardOpenPath6<A, B, C, D, E, F, G, H, I, J, K>.`o-→`(node: K) = NeoMatchablePath6(
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
    PathDirection.Forwards,
    NodeReference(node),
    ref
)

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>> NoDirOpenPath6<A, B, C, D, E, F, G, H, I, J>.`-o-`(node: Searchable<I>) =
    NeoMatchablePath6(
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
        PathDirection.None,
        node,
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>> NoDirOpenPath6<A, B, C, D, E, F, G, H, I, J>.`-o-`(node: KFunction<I>) =
    NeoMatchablePath6(
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
        PathDirection.None,
        node {},
        ref
    )

infix fun <A : Node<*>, B : Relationship<*, *, *>,
        C : Node<*>, D : Relationship<*, *, *>,
        E : Node<*>, F : Relationship<*, *, *>,
        G : Node<*>, H : Relationship<*, *, *>,
        I : Node<*>, J : NonDirectionalRelationship<I, *>> NoDirOpenPath6<A, B, C, D, E, F, G, H, I, J>.`-o-`(node: I) =
    NeoMatchablePath6(
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
        PathDirection.None,
        NodeReference(node),
        ref
    )

