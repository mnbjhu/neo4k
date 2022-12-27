package e2e

import e2e.schemas.FriendsWith
import e2e.schemas.UserNode
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.paths.`-o-`
import uk.gibby.neo4k.paths.`o-→`
import util.GraphTest

class PathsTest: GraphTest() {
    @Test
    fun createLongPath(){
        graph.query {
            val alice = create(::UserNode{ it[firstName] = "Alice"; it[surname] = "A"; it[password] = "Password123"})
            val bob = create(::UserNode{ it[firstName] = "Bob"; it[surname] = "B"; it[password] = "Password123"})
            val charlie = create(::UserNode{ it[firstName] = "Charlie"; it[surname] = "C"; it[password] = "Password123"})
            val dennis = create(::UserNode{ it[firstName] = "Dennis"; it[surname] = "D"; it[password] = "Password123"})
            val evan = create(::UserNode{ it[firstName] = "Ewan"; it[surname] = "E"; it[password] = "Password123"})
            val fred = create(::UserNode{ it[firstName] = "Fred"; it[surname] = "F"; it[password] = "Password123"})
            val georgie = create(::UserNode{ it[firstName] = "Georgie"; it[surname] = "G"; it[password] = "Password123"})
            val helen = create(::UserNode{ it[firstName] = "Helen"; it[surname] = "H"; it[password] = "Password123"})
            create(
                alice `-o-` ::FriendsWith `-o-` bob `-o-` ::FriendsWith `-o-` charlie `-o-` ::FriendsWith `-o-` dennis `-o-`
                ::FriendsWith `-o-` evan `-o-` ::FriendsWith `-o-` fred `-o-` ::FriendsWith `-o-` georgie `-o-` ::FriendsWith `-o-` helen
            )
        }
    }
}



class Paths : GraphTest(){
    @Test
    fun createPath3(){
        graph.query {
            create(::UserNode{ it[firstName] = "" })
        }

    }
}

