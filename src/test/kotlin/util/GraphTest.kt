package util

import org.junit.jupiter.api.BeforeEach
import uk.gibby.neo4k.core.Graph

abstract class GraphTest {
    protected val graph = Graph(
        name = this::class.qualifiedName!!.replace("_", ".").lowercase(),
        host = "localhost",
        username = "test",
        password = "test"
    )
    @BeforeEach
    fun clearGraph(){
        graph.create()
        graph.delete()
    }
}