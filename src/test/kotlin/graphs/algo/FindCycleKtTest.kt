package graphs.algo

import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * FindCycleKtTest class contains unit tests for detecting cycles in both undirected and directed graphs.
 * It verifies the functionality of the `findCycle` method by checking graphs with and without cycles.
 */
class FindCycleKtTest {

    /**
     * Tests that an undirected graph without a cycle returns an empty path list.
     */
    @Test
    fun `undirected graph without cycle`() {
        val graph = UndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = findCycle(graph, 1)
        assertEquals(0, pathList.size)
    }

    /**
     * Tests that an undirected graph with a cycle returns the correct path list.
     */
    @Test
    fun `undirected graph with cycle`() {
        val graph = UndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
            addEdge(3, 1, 3)
        }

        val pathList = findCycle(graph, 1)
        assertEquals(4, pathList.size)
        assertEquals(listOf<Long>(1, 2, 3, 1), pathList)
    }

    /**
     * Tests that a directed graph without a cycle returns an empty path list.
     */
    @Test
    fun `directed graph without cycle`() {
        val graph = DirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
            addEdge(1, 3, 3)
        }

        val pathList = findCycle(graph, 1)
        assertEquals(0, pathList.size)
    }

    /**
     * Tests that a directed graph with a cycle returns the correct path list.
     */
    @Test
    fun `directed graph with cycle`() {
        val graph = DirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(2, 1, 1)
            addEdge(1, 3, 2)
            addEdge(3, 2, 3)
        }

        val pathList = findCycle(graph, 1)
        println(pathList)
        assertEquals(4, pathList.size)
        assertEquals(listOf<Long>(1, 3, 2, 1), pathList)
    }
}
