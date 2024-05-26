package graphs.algo

import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class MinimalPathDijkstraKtTest {

    // algorithm for weighted graphs
    // weighted undirected
    // weighted directed

    @Test
    fun `path in undirected graph`() {
        val graph = WeightedUndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = MinimalPathDijkstra(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3), pathList)
    }

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

        val pathList = MinimalPathDijkstra(graph, 1, 4)
        assertEquals(0, pathList.size)
    }

    @Test
    fun `path in directed graph`() {
        val graph = WeightedDirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = MinimalPathDijkstra(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3), pathList)
    }

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

        val pathList = MinimalPathDijkstra(graph, 1, 4)
        assertEquals(0, pathList.size)
    }
}
