package graphs.algo

import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * MinimalPathDijkstraKtTest class contains unit tests for finding the minimal path
 * in weighted undirected and directed graphs using Dijkstra's algorithm.
 */
class MinimalPathDijkstraKtTest {

    /**
     * Tests finding a path in a weighted undirected graph.
     * Verifies that the path from vertex 1 to vertex 3 is correct.
     */
    @Test
    fun `path in undirected graph`() {
        val graph = WeightedUndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = minimalPathDijkstra(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3), pathList)
    }

    /**
     * Tests finding no path in a weighted undirected graph.
     * Verifies that there is no path from vertex 1 to vertex 4.
     */
    @Test
    fun `no path in undirected graph`() {
        val graph = WeightedUndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addVertex(4)
            addEdge(1, 2, 1)
            addEdge(3, 4, 2)
        }

        val pathList = minimalPathDijkstra(graph, 1, 4)
        assertEquals(0, pathList.size)
    }

    /**
     * Tests finding a path in a weighted directed graph.
     * Verifies that the path from vertex 1 to vertex 3 is correct.
     */
    @Test
    fun `path in directed graph`() {
        val graph = WeightedDirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = minimalPathDijkstra(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3), pathList)
    }

    /**
     * Tests finding no path in a weighted directed graph.
     * Verifies that there is no path from vertex 1 to vertex 4.
     */
    @Test
    fun `no path in directed graph`() {
        val graph = WeightedDirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addVertex(4)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
            addEdge(4, 3, 3)
        }

        val pathList = minimalPathDijkstra(graph, 1, 4)
        assertEquals(0, pathList.size)
    }
    @Test
    fun twopaths() {
        val graph = WeightedDirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addVertex(4)
            addEdge(1, 2, 1, 10)
            addEdge(2, 3, 2, 10)
            addEdge(1, 4, 3, 1)
            addEdge(4, 3, 4, 1)
        }
        val pathList = minimalPathDijkstra(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 4, 3), pathList)
    }
}
