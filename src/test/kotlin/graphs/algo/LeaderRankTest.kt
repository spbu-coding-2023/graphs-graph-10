package graphs.algo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import graphs.primitives.*
import graphs.types.WeightedUndirectedGraph













class RemoveTest {

    @Test
    fun testRemoveVertexWithEdges() {
        val graph = WeightedUndirectedGraph()
        val v1 = graph.addVertex(1)
        val v2 = graph.addVertex(2)
        val v3 = graph.addVertex(3)
        graph.addEdge(1, 2, 1)
        graph.addEdge(2, 3, 2)
        graph.addEdge(3, 1, 3)

        remove(graph, v2)
        assertFalse(graph.vertices.contains(v2))

        graph.edges.forEach { edge ->
            assertNotEquals(v2, edge.vertices.first)
            assertNotEquals(v2, edge.vertices.second)
        }
    }

    @Test
    fun testRemoveIsolatedVertex() {
        val graph = WeightedUndirectedGraph()
        val v1 = graph.addVertex(1)
        remove(graph, v1)
        assertFalse(graph.vertices.contains(v1))
    }
}