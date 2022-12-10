package util

import org.junit.jupiter.api.BeforeEach
import uk.gibby.neo4k.core.Graph

abstract class GraphTest {
    protected val graph = Graph(
        name = this::class.qualifiedName!!.replace("_", "."),
        host = TestAuth.host,
        port = TestAuth.port,
        password = TestAuth.password
    )
    @BeforeEach
    fun clearGraph(){
        graph.delete()
    }
}