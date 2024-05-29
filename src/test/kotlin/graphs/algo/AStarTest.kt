package graphs.algo

import AStar
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AStarTest {
    @Test
    fun `path in undirected graph`() {
        val graph = WeightedUndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = AStar(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3), pathList)
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
        val pathList = AStar(graph, 1, 3)
        assertEquals(3, pathList.size)
        assertEquals(listOf<Long>(1, 4, 3), pathList)
    }
}
