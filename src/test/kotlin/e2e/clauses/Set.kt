package e2e.clauses

import e2e.schemas.FriendsWith
import e2e.schemas.UserNode
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.Set.Companion.set
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.`-o-`
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class Set: GraphTest() {
    @BeforeEach
    fun setup(){
        graph.query {
            val alice = create(::UserNode{ it[::firstName] = "Alice"; it[::surname] = "Williams"; it[::password] = "Password123"})
            val bob = create(::UserNode{ it[::firstName] = "Bob"; it[::surname] = "Johnson"; it[::password] = "Password123"})
            create(alice `-o-` ::FriendsWith{it[::related] = false} `-o-`  bob)
        }
    }

    @Test
    fun setNodeValue(){
        graph.query {
            val alice = match(::UserNode{it[::firstName] = "Alice"})
            set { alice.password to "Password123!" }
            alice.password
        } `should be equal to` listOf("Password123!")
    }

    @Test
    fun setNodeValueToReference(){
        graph.query {
            val (alice, bob) = match(::UserNode{it[::firstName] = "Alice"}, ::UserNode{it[::firstName] = "Bob"})
            set { alice.surname to bob.surname }
            alice.surname
        } `should be equal to` listOf("Johnson")
    }

    @Test
    fun setRelationValue(){
        graph.query {
            val (_, friendsWith, _) = match(::UserNode{it[::firstName] = "Alice"} `-o-` ::FriendsWith `-o-` ::UserNode{it[::firstName] = "Bob"})
            set { friendsWith.related to true }
            friendsWith.related
        } `should be equal to` listOf(true)
    }
}