
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be instance of`
import org.junit.jupiter.api.Test
import uk.gibby.neo4k.clauses.Create.Companion.create
import uk.gibby.neo4k.clauses.Delete.Companion.delete
import uk.gibby.neo4k.clauses.Match.Companion.match
import uk.gibby.neo4k.clauses.Set.Companion.set
import uk.gibby.neo4k.core.QueryScope
import uk.gibby.neo4k.core.invoke
import uk.gibby.neo4k.core.nullable
import uk.gibby.neo4k.core.of
import uk.gibby.neo4k.paths.`o-→`
import uk.gibby.neo4k.returns.ReturnValue.Companion.createInstance
import uk.gibby.neo4k.returns.ReturnValue.Companion.createReference
import uk.gibby.neo4k.returns.generic.Nullable
import uk.gibby.neo4k.returns.generic.StructReturn
import uk.gibby.neo4k.returns.graph.entities.Node
import uk.gibby.neo4k.returns.graph.entities.DirectionalRelationship
import uk.gibby.neo4k.returns.primitives.StringReturn
import uk.gibby.neo4k.returns.util.ReturnScope
import uk.gibby.neo4k.returns.util.ReturnValueType


class TestNode(val myString: Nullable<String, StringReturn>): Node<Unit>(){
    override fun ReturnScope.decode() {}
}
class HasOther: DirectionalRelationship<TestNode, TestNode, Unit>() {
    override fun ReturnScope.decode() {
        TODO("Not yet implemented")
    }
}
class TestReturn(val myString: Nullable<String, StringReturn>): StructReturn<String?>() {
    override fun StructParamMap.encodeStruct(value: String?) {
        myString[value]
    }

    override fun ReturnScope.decode() = ::myString.result()
}

class CreateReferenceTest {
    @Test
    fun `Create Reference`(){
        createReference(::TestReturn, "TestRef").also {
            it.type `should be instance of` ReturnValueType.Reference::class
            it.getString() `should be equal to` "TestRef"
            it.myString.getString() `should be equal to` "TestRef[0]"
        }
    }
    @Test
    fun `Create Instance`(){
        val instance = createInstance(::TestReturn, "TestCreate")
        instance.type `should be instance of` ReturnValueType.Instance::class
        instance.getString() `should be equal to` "['TestCreate']"
    }
    @Test
    fun `Create Null Attribute Instance`(){
        val instance = createInstance(::TestReturn, null)
        instance.type `should be instance of` ReturnValueType.Instance::class
        instance.getString() `should be equal to` "[NULL]"

    }
    @Test
    fun `Create Null Instance`(){
        val instance = nullable(::StringReturn) of null
        instance.getString() `should be equal to` "NULL"
    }
    @Test
    fun `Create not-null instance`(){
        val instance = nullable(::StringReturn) of "Hello"
        instance.getString() `should be equal to` "'Hello'"

    }
    @Test
    fun `Create node reference`(){
        with(QueryScope()){
            val first = createReference(::TestNode, "some_ref")
            val (node) = match(first `o-→` ::HasOther `o-→` first)
            val node2 = create(::TestNode{ it[myString] = "some string" })
            create(node2 `o-→` ::HasOther `o-→` ::TestNode{ it[myString] = "other string" })

            delete(node, node2)
            set {
                node.myString to "Some String"
                node2.myString to "Other String"
            }
        }
    }
}

