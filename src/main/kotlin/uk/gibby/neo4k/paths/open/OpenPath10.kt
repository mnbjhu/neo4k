package uk.gibby.neo4k.paths.open

import uk.gibby.neo4k.core.NodeParamMap
import uk.gibby.neo4k.core.RelationParamMap
import uk.gibby.neo4k.paths.matchable.MatchablePath10
import uk.gibby.neo4k.paths.matchable.MatchablePath9
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.util.NodeReference
import kotlin.reflect.KFunction

class OpenPath10<A: Node<*>, B: DirectionalRelationship<A, C, *>, C: Node<*>, D: DirectionalRelationship<C, E, *>, E: Node<*>, F: DirectionalRelationship<E, G, *>, G: Node<*>, H: DirectionalRelationship<G, I, *>, I: Node<*>, J: DirectionalRelationship<I, K, *>, K: Node<*>, L: DirectionalRelationship<K, M, *>, M: Node<*>, N: DirectionalRelationship<M, O, *>, O: Node<*>, P: DirectionalRelationship<O, Q, *>, Q: Node<*>, R: DirectionalRelationship<Q, S, *>, S: Node<*>>(private val prev: MatchablePath9<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q>, internal val ninthToTenth: RelationParamMap<R>){operator fun minus(node: NodeParamMap<S>) =
    MatchablePath10(
        prev.first,
        prev.firstToSecond,
        prev.second,
        prev.secondToThird,
        prev.third,
        prev.thirdToFourth,
        prev.fourth,
        prev.fourthToFifth,
        prev.fifth,
        prev.fifthToSixth,
        prev.sixth,
        prev.sixthToSeventh,
        prev.seventh,
        prev.seventhToEighth,
        prev.eighth,
        prev.eighthToNinth,
        prev.ninth,
        ninthToTenth,
        node,
        prev.ref
    )
    operator fun minus(nodeRef: C) =
        MatchablePath10(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            prev.fifthToSixth,
            prev.sixth,
            prev.sixthToSeventh,
            prev.seventh,
            prev.seventhToEighth,
            prev.eighth,
            prev.eighthToNinth,
            prev.ninth,
            ninthToTenth,
            NodeReference(nodeRef),
            prev.ref
        )
    operator fun minus(nodeType: KFunction<C>) =
        MatchablePath10(
            prev.first,
            prev.firstToSecond,
            prev.second,
            prev.secondToThird,
            prev.third,
            prev.thirdToFourth,
            prev.fourth,
            prev.fourthToFifth,
            prev.fifth,
            prev.fifthToSixth,
            prev.sixth,
            prev.sixthToSeventh,
            prev.seventh,
            prev.seventhToEighth,
            prev.eighth,
            prev.eighthToNinth,
            prev.ninth,
            ninthToTenth,
            NodeParamMap(nodeType.returnType),
            prev.ref
        )
}