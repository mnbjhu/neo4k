package e2e.schemas

import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.UnitDirectionalRelationship
import uk.gibby.neo4k.returns.graph.entities.UnitNonDirectionalRelationship
import uk.gibby.neo4k.returns.primitives.BooleanReturn
import uk.gibby.neo4k.returns.primitives.StringReturn
import uk.gibby.neo4k.returns.util.ReturnScope

class UserNode(
    val firstName: StringReturn,
    val surname: StringReturn,
    val password: StringReturn,
): Node<User>() {
    override fun ReturnScope.decode() = User(
        ::firstName.result(),
        ::surname.result(),
        ::password.result()
    )
}

data class User(val firstName: String, val surname: String, val password: String)
class FriendsWith: UnitNonDirectionalRelationship<UserNode>()
