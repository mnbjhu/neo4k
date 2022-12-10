package uk.gibby.neo4k.paths.closed

import uk.gibby.neo4k.returns.empty.EmptyReturn
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship


data class Path2<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>>(val first: A, val firstToSecond: B, val second: C, internal val name: String): EmptyReturn()
data class Path3<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, internal val name: String): EmptyReturn()
data class Path4<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, internal val name: String): EmptyReturn()
data class Path5<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, internal val name: String): EmptyReturn()
data class Path6<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>, out J: DirectionalRelationship<I, K, *>, out K: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, val fifthToSixth: J, val sixth: K, internal val name: String): EmptyReturn()
data class Path7<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>, out J: DirectionalRelationship<I, K, *>, out K: Node<*>, out L: DirectionalRelationship<K, M, *>, out M: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, val fifthToSixth: J, val sixth: K, val sixthToSeventh: L, val seventh: M, internal val name: String): EmptyReturn()
data class Path8<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>, out J: DirectionalRelationship<I, K, *>, out K: Node<*>, out L: DirectionalRelationship<K, M, *>, out M: Node<*>, out N: DirectionalRelationship<M, O, *>, out O: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, val fifthToSixth: J, val sixth: K, val sixthToSeventh: L, val seventh: M, val seventhToEighth: N, val eighth: O, internal val name: String): EmptyReturn()
data class Path9<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>, out J: DirectionalRelationship<I, K, *>, out K: Node<*>, out L: DirectionalRelationship<K, M, *>, out M: Node<*>, out N: DirectionalRelationship<M, O, *>, out O: Node<*>, out P: DirectionalRelationship<O, Q, *>, out Q: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, val fifthToSixth: J, val sixth: K, val sixthToSeventh: L, val seventh: M, val seventhToEighth: N, val eighth: O, val eighthToNinth: P, val ninth: Q, internal val name: String): EmptyReturn()
data class Path10<out A: Node<*>, out B: DirectionalRelationship<A, C, *>, out C: Node<*>, out D: DirectionalRelationship<C, E, *>, out E: Node<*>, out F: DirectionalRelationship<E, G, *>, out G: Node<*>, out H: DirectionalRelationship<G, I, *>, out I: Node<*>, out J: DirectionalRelationship<I, K, *>, out K: Node<*>, out L: DirectionalRelationship<K, M, *>, out M: Node<*>, out N: DirectionalRelationship<M, O, *>, out O: Node<*>, out P: DirectionalRelationship<O, Q, *>, out Q: Node<*>, out R: DirectionalRelationship<Q, S, *>, out S: Node<*>>(val first: A, val firstToSecond: B, val second: C, val secondToThird: D, val third: E, val thirdToFourth: F, val fourth: G, val fourthToFifth: H, val fifth: I, val fifthToSixth: J, val sixth: K, val sixthToSeventh: L, val seventh: M, val seventhToEighth: N, val eighth: O, val eighthToNinth: P, val ninth: Q, val ninthToTenth: R, val tenth: S, internal val name: String): EmptyReturn()