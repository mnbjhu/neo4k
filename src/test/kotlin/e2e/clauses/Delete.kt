package e2e.clauses

import e2e.schemas.FriendsWith
import e2e.schemas.UserNode
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.DetachDelete.Companion.detachDelete
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.`-o-`
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class Delete: GraphTest() {

    @BeforeEach
    fun setup(){
        graph.query {
            val alice = create(::UserNode{ it[firstName] = "Alice"; it[surname] = "Williams"; it[password] = "Password123"})
            val bob = create(::UserNode{ it[firstName] = "Bob"; it[surname] = "Johnson"; it[password] = "Password123"})
            create(alice `-o-` ::FriendsWith `-o-` bob)
        }
    }

    @Test
    fun deleteNode(){
        graph.query {
            val bob = match(::UserNode{it[firstName] = "Bob"})
            detachDelete(bob)
        }
        graph.query { match(::UserNode{it[firstName] = "Bob"}) }`should be equal to` listOf()
    }
    @Test
    fun deleteRelation(){
        graph.query {
            val (_, relation) = match(::UserNode `-o-` ::FriendsWith `-o-` ::UserNode)
            detachDelete(relation)
        }
        graph.query {
            val (user) = match(::UserNode `-o-` ::FriendsWith `-o-` ::UserNode)
            user.firstName
        }`should be equal to` listOf()
    }
}