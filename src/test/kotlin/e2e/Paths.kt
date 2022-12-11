package e2e

import e2e.schemas.FriendsWith
import e2e.schemas.UserNode
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.core.invoke
import util.GraphTest
/*
class Paths: GraphTest() {
    @Test
    fun createLongPath(){
        graph.query {
            val alice = create(::UserNode{ it[::firstName] = "Alice"; it[::surname] = "A"; it[::password] = "Password123"})
            val bob = create(::UserNode{ it[::firstName] = "Bob"; it[::surname] = "A"; it[::password] = "Password123"})
            val charlie = create(::UserNode{ it[::firstName] = "Charlie"; it[::surname] = "A"; it[::password] = "Password123"})
            val dennis = create(::UserNode{ it[::firstName] = "Dennis"; it[::surname] = "A"; it[::password] = "Password123"})
            val evan = create(::UserNode{ it[::firstName] = "Ewan"; it[::surname] = "A"; it[::password] = "Password123"})
            val fred = create(::UserNode{ it[::firstName] = "Fred"; it[::surname] = "A"; it[::password] = "Password123"})
            val georgie = create(::UserNode{ it[::firstName] = "Georgie"; it[::surname] = "A"; it[::password] = "Password123"})
            val helen = create(::UserNode{ it[::firstName] = "Helen"; it[::surname] = "A"; it[::password] = "Password123"})
            create(alice `--` (::FriendsWith) `→` bob `--` ::FriendsWith `→` charlie `--` ::FriendsWith  dennis)
        }
    }
}


 */
