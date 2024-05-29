package graphs.algo

import graphs.types.WeightedDirectedGraph
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import graphs.types.WeightedUndirectedGraph

class LeaderRankTest {

    @Test
    fun testLeaderRankSingleNode() {
        val graph = WeightedDirectedGraph()
        val v1 = graph.addVertex(1)

        val result = LeaderRank(graph, 0.85, 0.001)

        assertEquals(1, result.size)
        assertEquals(v1, result[0].first)
        assertEquals(0.075, result[0].second, 0.001)
    }

    @Test
    fun testLeaderRankDisconnectedGraph() {
        val graph = WeightedDirectedGraph()
        val v1 = graph.addVertex(1)
        val v2 = graph.addVertex(2)
        val v3 = graph.addVertex(3)

        val result = LeaderRank(graph, 0.15, 0.001)

        assertEquals(3, result.size)
        assertEquals(v1, result[0].first)
        assertEquals(0.2125, result[0].second, 0.01)
        assertEquals(v2, result[1].first)
        assertEquals(0.2125, result[1].second, 0.01)
        assertEquals(v3, result[2].first)
        assertEquals(0.2125, result[2].second, 0.01)
    }

    @Test
    fun testLeaderRankFullyConnectedGraph() {
        val graph = WeightedDirectedGraph()
        val v1 = graph.addVertex(1)
        val v2 = graph.addVertex(2)
        val v3 = graph.addVertex(3)

        graph.addEdge(1, 2, 1, 1L)
        graph.addEdge(1, 3, 2, 1L)
        graph.addEdge(2, 3, 3, 1L)

        val result = LeaderRank(graph, 0.85, 0.001)

        assertEquals(3, result.size)
        assertTrue(result[0].second > result[1].second)
        assertTrue(result[1].second > result[2].second)
    }
}

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
