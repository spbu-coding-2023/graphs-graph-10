package graphs.algo

import graphs.primitives.Graph
import graphs.types.UndirectedGraph
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

//example 1:
//  1 - 0 - 3
//  | /     |
//  2       4
//
//example 2:
//  0     3
//  | \  / \
//  |  1    5
//  | /| \ /
//  2  6  4
//
//example 3:
// 0 - 1 - 2 - 3 - 4
class FindBridgeTest {
    private lateinit var sampleGraph: Graph
    @Test
    fun `null graph return null`() {
        sampleGraph = UndirectedGraph()

        assertEquals(findBridges(sampleGraph), mutableListOf())
    }

    @Test
    fun `vertices that form a cycle should not have bridges`() {
        sampleGraph = UndirectedGraph().apply {
            addEdge(0, 1, 0)
            addEdge(1, 2, 1)
            addEdge(2, 3, 2)
            addEdge(3, 4, 3)
            addEdge(4, 5, 4)
            addEdge(5, 6, 5)
            addEdge(6, 7, 6)
            addEdge(7, 8, 7)
            addEdge(8, 9, 8)
            addEdge(9, 10, 9)
            addEdge(10, 0, 10)
        }

        assertEquals(findBridges(sampleGraph), mutableListOf())
    }
    @Test
    fun `1 cycle and 2 bridges`() { //example 1
        sampleGraph = UndirectedGraph().apply {
            addEdge(1, 0 , 0)
            addEdge(2, 1, 1)
            addEdge(2, 0 , 2)
            addEdge(0 , 3 , 3)
            addEdge(3, 4, 4)
        }
        val expectedBridges = listOf(Pair(0L , 3L), Pair(3L, 4L))
        val bridges = findBridges(sampleGraph)
        assertEquals(expectedBridges.size, bridges.size, "The number of bridges does not match the expected number")
        for ((u, v) in bridges) {
            assertTrue((Pair(u, v) in expectedBridges || Pair(v, u) in expectedBridges), "($u, $v) not bridge")
        }
    }
    @Test
    fun `2 cycle and 1 bridge`() { //example 2
        sampleGraph = UndirectedGraph().apply {
            addEdge(1, 0, 0)
            addEdge(2, 1, 1)
            addEdge(2, 0, 2)
            addEdge(1, 6, 3)
            addEdge(1, 3, 4)
            addEdge(1, 4, 5)
            addEdge(4, 5, 6)
            addEdge(3, 5, 7)
        }

        val expectedBridges = listOf(Pair(1L, 6L))
        val bridges = findBridges(sampleGraph)
        assertEquals(expectedBridges.size, bridges.size, "The number of bridges does not match the expected number")
        for ((u, v) in bridges) {
            assertTrue((Pair(u, v) in expectedBridges || Pair(v, u) in expectedBridges), "($u, $v) not bridge")
        }
    }

    @Test
    fun `every vertices is bridge`() { //example 3
        sampleGraph = UndirectedGraph().apply {
            addEdge(0, 1 , 0)
            addEdge(1, 2, 1)
            addEdge(2, 3 , 2)
            addEdge(3 , 4 , 3)
            addEdge(4, 5, 4)
        }
        val expectedBridges = listOf(Pair(0L, 1L), Pair(1L, 2L), Pair(2L, 3L), Pair(3L, 4L), Pair(4L, 5L))
        val bridges = findBridges(sampleGraph)
        assertEquals(expectedBridges.size, bridges.size, "The number of bridges does not match the expected number")
        for ((u, v) in bridges) {
            assertTrue((Pair(u, v) in expectedBridges || Pair(v, u) in expectedBridges), "($u, $v) not bridge")
        }
    }
}
