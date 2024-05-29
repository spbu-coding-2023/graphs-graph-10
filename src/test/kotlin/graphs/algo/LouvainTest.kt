package graphs.algo

import graphs.primitives.Graph
import graphs.types.UndirectedGraph
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

/*since the algorithm can form communities in different ways,
the tests will consider only obvious situations when
the number of communities is known
 */
class LouvainTest {
    private lateinit var sampleGraph: Graph

    @Test
    fun `5 vertices are connected each to each and 1 is connected to one`() {
        sampleGraph = UndirectedGraph().apply {
            addEdge(1, 2, 0)
            addEdge(1, 3, 1)
            addEdge(1, 4, 2)
            addEdge(1, 5, 3)
            addEdge(2, 3, 4)
            addEdge(2, 4, 5)
            addEdge(2, 5, 6)
            addEdge(3, 4, 7)
            addEdge(4, 5, 8)
            addEdge(6, 1, 9)
        }
        assertEquals(louvain(sampleGraph).size, 2 , "the graph should be divided into 2 communities")
    }
    @Test
    fun `only 1 cycle`() {
        sampleGraph = UndirectedGraph().apply {
            addEdge(1, 2, 0)
            addEdge(2, 3, 1)
            addEdge(3, 4, 2)
            addEdge(4, 5, 3)
            addEdge(5, 6, 4)
            addEdge(6, 7, 5)
            addEdge(7, 8, 6)
            addEdge(8, 9, 7)
            addEdge(9, 10, 8)
            addEdge(10, 11, 9)
            addEdge(11, 1, 10)
        }
        assertEquals(louvain(sampleGraph).size, 1 , "Graph has 1 community")
    }
    @Test
    fun `all vertices doesn't have edge`() {
        sampleGraph = UndirectedGraph().apply {
            for (i in 0 until 1000) {
                addVertex(i.toLong())
            }
        }
        assertEquals(louvain(sampleGraph).size, 1000, "Graph has 1000 communities with 1 vertex")
    }
    @Test
    fun `2 cycles`() {
        sampleGraph = UndirectedGraph().apply {
            addEdge(1, 2, 0)
            addEdge(2, 3, 1)
            addEdge(1, 3, 2)
            addEdge(10, 1, 10)
            addEdge(10, 2, 11)
            addEdge(10, 3, 12)

            addEdge(4, 5 , 3)
            addEdge(5, 6, 4)
            addEdge(4, 6, 5)
            addEdge(4, 7 , 7)
            addEdge(4, 6, 8)
            addEdge(4, 5, 9)
        }
        assertEquals(louvain(sampleGraph).size, 2, "Graph has 2 community")
    }
}
