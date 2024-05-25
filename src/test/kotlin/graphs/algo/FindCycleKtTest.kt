package graphs.algo

import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FindCycleKtTest {

    // undirected | directed
    // has no cycle
    // has cycle

    @Test
    fun `undirected graph without cycle`() {
        val graph = UndirectedGraph<Long, Long>().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
        }

        val pathList = findCycle(graph, 1)
        assertEquals(0, pathList.size)
    }

    @Test
    fun `undirected graph with cycle`() {
        val graph = UndirectedGraph<Long, Long>().apply {
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

    @Test
    fun `directed graph without cycle`() {
        val graph = DirectedGraph<Long, Long>().apply {
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

    @Test
    fun `directed graph with cycle`() {
        val graph = DirectedGraph<Long, Long>().apply {
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
