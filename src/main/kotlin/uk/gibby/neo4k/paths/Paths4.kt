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



data class NeoPath4<A: ReturnValue<*>, B: ReturnValue<*>, C: ReturnValue<*>, D: ReturnValue<*>, E: ReturnValue<*>, F: ReturnValue<*>, G: ReturnValue<*>>(
    val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, internal val ref: String
): EmptyReturn()

class NeoMatchablePath4<A: Node<*>, B: Relationship<*, *, *>, C: Node<*>, D: Relationship<*, *, *>, E: Node<*>, F: Relationship<*, *, *>, G: Node<*>>(
    val first: Searchable<A>, val firstToSecond: RelationParamMap<B>, val firstToSecondDir: PathDirection, val second: Searchable<C>, val secondToThird: RelationParamMap<D>, val secondToThirdDir: PathDirection, val third: Searchable<E>, val thirdToFourth: RelationParamMap<F>, val thirdToFourthDir: PathDirection, val fourth: Searchable<G>, override val ref: String
): Matchable<NeoPath4<A, B, C, D, E, F, G>>, Creatable<NeoPath4<A, B, C, D, E, F, G>>{
    override fun getCreateString() = "(${ first.getCreateString() })${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}[${firstToSecond.getMatchString()}]${if (firstToSecondDir != PathDirection.Backwards) "->" else "-"}(${second.getCreateString()})${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}[${secondToThird.getMatchString()}]${if (secondToThirdDir != PathDirection.Backwards) "->" else "-"}(${third.getCreateString()})${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}[${thirdToFourth.getMatchString()}]${if (thirdToFourthDir != PathDirection.Backwards) "->" else "-"}(${fourth.getCreateString()})"
    override fun getMatchString() = "(${ first.getMatchString() })${if (firstToSecondDir == PathDirection.Backwards) "<-" else "-"}[${firstToSecond.getMatchString()}]${if (firstToSecondDir == PathDirection.Forwards) "->" else "-"}(${second.getCreateString()})${if (secondToThirdDir == PathDirection.Backwards) "<-" else "-"}[${secondToThird.getMatchString()}]${if (secondToThirdDir == PathDirection.Forwards) "->" else "-"}(${third.getCreateString()})${if (thirdToFourthDir == PathDirection.Backwards) "<-" else "-"}[${thirdToFourth.getMatchString()}]${if (thirdToFourthDir == PathDirection.Forwards) "->" else "-"}(${fourth.getCreateString()})"
    override fun getReference() = NeoPath4(first.getReference(), firstToSecond.getReference(), second.getReference(), secondToThird.getReference(), third.getReference(), thirdToFourth.getReference(), fourth.getReference(), ref)

}

class NoDirOpenPath4<A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val ref: String
)

class ForwardOpenPath4<A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val ref: String
)

class BackwardsOpenPath4<A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>(
    val first: Searchable<A>,
    val firstToSecond: RelationParamMap<B>,
    val firstToSecondDir: PathDirection,
    val second: Searchable<C>,
    val secondToThird: RelationParamMap<D>,
    val secondToThirdDir: PathDirection,
    val third: Searchable<E>,
    val thirdToFourth: RelationParamMap<F>,
    val ref: String
)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>NeoMatchablePath3<A, B, C, D, E>.`o-→`(relation: RelationParamMap<F>) = ForwardOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>NeoMatchablePath3<A, B, C, D, E>.`o-→`(relation: KFunction<F>) = ForwardOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>NeoMatchablePath3<A, B, C, D, E>.`←-o`(relation: RelationParamMap<F>) = BackwardsOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>NeoMatchablePath3<A, B, C, D, E>.`←-o`(relation: KFunction<F>) = BackwardsOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>NeoMatchablePath3<A, B, C, D, E>.`-o-`(relation: RelationParamMap<F>) = NoDirOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>NeoMatchablePath3<A, B, C, D, E>.`-o-`(relation: KFunction<F>) = NoDirOpenPath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir, third,  relation{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>BackwardsOpenPath4<A, B, C, D, E, F, G>.`←-o`(node: Searchable<G>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Backwards,  node, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>BackwardsOpenPath4<A, B, C, D, E, F, G>.`←-o`(node: KFunction<G>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Backwards,  node{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<G, E, *>,
        G: Node<*>>BackwardsOpenPath4<A, B, C, D, E, F, G>.`←-o`(node: G) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Backwards,  NodeReference(node), ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>ForwardOpenPath4<A, B, C, D, E, F, G>.`o-→`(node: Searchable<G>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Forwards,  node, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>ForwardOpenPath4<A, B, C, D, E, F, G>.`o-→`(node: KFunction<G>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Forwards,  node{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: DirectionalRelationship<E, G, *>,
        G: Node<*>>ForwardOpenPath4<A, B, C, D, E, F, G>.`o-→`(node: G) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.Forwards,  NodeReference(node), ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>NoDirOpenPath4<A, B, C, D, E, F>.`-o-`(node: Searchable<E>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.None,  node, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>NoDirOpenPath4<A, B, C, D, E, F>.`-o-`(node: KFunction<E>) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.None,  node{}, ref)

infix fun <A: Node<*>, B: Relationship<*, *, *>,
        C: Node<*>, D: Relationship<*, *, *>,
        E: Node<*>, F: NonDirectionalRelationship<E, *>>NoDirOpenPath4<A, B, C, D, E, F>.`-o-`(node: E) = NeoMatchablePath4(first, firstToSecond, firstToSecondDir, second, secondToThird, secondToThirdDir,third, thirdToFourth,PathDirection.None,  NodeReference(node), ref)