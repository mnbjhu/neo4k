package util

import org.junit.jupiter.api.BeforeEach
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase
import uk.gibby.neo4k.core.Graph

abstract class GraphTest {
    protected val graph = Graph(
        name = this::class.qualifiedName!!.replace("_", "."),
        host = "localhost",
        username = "test",
        password = "test"
    )
    @BeforeEach
    fun clearGraph(){
        graph.delete()
    }
}