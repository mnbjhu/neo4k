package e2e.types.node_returns

import e2e.schemas.User
import e2e.schemas.UserNode
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.functions.id
import util.GraphTest

class UserExample: GraphTest() {
    @Test
    fun returnNodeRef(){
        graph.query {
            create(::UserNode{
                it[firstName] = "Test"
                it[surname] = "User"
                it[password] = "Password123"
            })
        } `should be equal to` listOf(User("Test", "User", "Password123"))
    }

    @Test
    fun idTest(){
        graph.query {
            val node = create(::UserNode{
                it[firstName] = "Test"
                it[surname] = "User"
                it[password] = "Password123"
            })
            id(node)
        }.first() `should be equal to` 0L
    }
}