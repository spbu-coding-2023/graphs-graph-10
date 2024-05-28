package algoTest

import graphs.algo.fordBellman
import graphs.primitives.Graph
import graphs.types.WeightedDirectedGraph
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class fordBellmanTest {
    private lateinit var sampleGraph: Graph
    @Test
    fun `positive and negative graph`() {
        sampleGraph = WeightedDirectedGraph().apply {
            addEdge(1, 2, 0, -1)
            addEdge(2, 3, 1, 2)
            addEdge(3, 4, 2, -3)
            addEdge(4, 2, 3, 1)
            addEdge(2, 4, 4, 2)
            addEdge(4, 5, 5, 5)
            addEdge(1, 5, 6, 4)
            addEdge(2, 5, 7, 3)

        }


        assertNull(fordBellman(sampleGraph, 1, 3) , "Negative cycle should return null")
        assertEquals(fordBellman(sampleGraph, 1, 2)?.second, -1, "Path from '1' to '2' was be -1")
        assertEquals(fordBellman(sampleGraph, 3, 5)?.second, 1, "Path from '1' to '2' was be -1")

    }
    @Test
    fun `only negative graph should return null`() {
        sampleGraph = WeightedDirectedGraph().apply {
            addEdge(1, 2, 0, -1)
            addEdge(2, 3, 1, -2)
            addEdge(3, 4, 2, -3)
            addEdge(4, 2, 3, -1)
            addEdge(2, 4, 4, -2)
            addEdge(4, 5, 5, -5)
            addEdge(1, 5, 6, -4)
            addEdge(2, 5, 7, -3)

        }
        for (u in sampleGraph.vertices) {
            for (v in sampleGraph.vertices) {
                if (u != v)
                    assertNull(fordBellman(sampleGraph, u.element, v.element), "Negative cycle should return null")
            }
        }
    }
    @Test
    fun `only positive graph`() {
        sampleGraph = WeightedDirectedGraph().apply {
            addEdge(1, 2, 0, -1)
            addEdge(2, 3, 1, -2)
            addEdge(3, 4, 2, -3)
            addEdge(4, 2, 3, -1)
            addEdge(2, 4, 4, -2)
            addEdge(4, 5, 5, -5)
            addEdge(1, 5, 6, -4)
            addEdge(2, 5, 7, -3)

        }
    }
}