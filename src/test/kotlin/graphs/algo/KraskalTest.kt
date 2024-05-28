package graphs.algo


import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KruskalTest {

    @Test
    fun SizeOfMST() {
        val graph = WeightedUndirectedGraph().apply() {
            addEdge(0, 1, 1, 7)
            addEdge(0, 2, 2, 8)
            addEdge(1, 2, 3, 11)
            addEdge(1, 3, 4, 2)
            addEdge(2, 3, 5, 6)
            addEdge(2, 4, 6, 9)
            addEdge(3, 4, 7, 11)
            addEdge(3, 5, 8, 9)
            addEdge(4, 5, 9, 10)
            addVertex(0, "A")
            addVertex(1, "B")
            addVertex(2, "C")
            addVertex(3, "D")
            addVertex(4, "E")
            addVertex(5, "F")
        }
        val minimalSpanningtree = Kruskal(graph)
        assertEquals(minimalSpanningtree.size, 5)
    }

    @Test
    fun SumOfWeightsInMST() {
        val graph = WeightedUndirectedGraph().apply() {
            addEdge(0, 1, 1, 7)
            addEdge(0, 2, 2, 8)
            addEdge(1, 2, 3, 11)
            addEdge(1, 3, 4, 2)
            addEdge(2, 3, 5, 6)
            addEdge(2, 4, 6, 9)
            addEdge(3, 4, 7, 11)
            addEdge(3, 5, 8, 9)
            addEdge(4, 5, 9, 10)
            addVertex(0, "A")
            addVertex(1, "B")
            addVertex(2, "C")
            addVertex(3, "D")
            addVertex(4, "E")
            addVertex(5, "F")
        }
        val minimalSpanningtree = Kruskal(graph)
        var sum: Long = 0
        minimalSpanningtree.forEach { it ->
            sum += it.weight ?: 0L
        }
        assertEquals(33, sum)
    }

    @Test
    fun PickedEdgesInMst() {
        val graph = WeightedUndirectedGraph().apply() {
            addEdge(0, 1, 1, 7)
            addEdge(0, 2, 2, 8)
            addEdge(1, 2, 3, 11)
            addEdge(1, 3, 4, 2)
            addEdge(2, 3, 5, 6)
            addEdge(2, 4, 6, 9)
            addEdge(3, 4, 7, 11)
            addEdge(3, 5, 8, 9)
            addEdge(4, 5, 9, 10)
            addVertex(0, "A")
            addVertex(1, "B")
            addVertex(2, "C")
            addVertex(3, "D")
            addVertex(4, "E")
            addVertex(5, "F")
        }
        val minimalSpanningtree = Kruskal(graph)
        val listOfEdgeIds = mutableListOf<Long?>()
        minimalSpanningtree.forEach { it ->
            listOfEdgeIds.add(it.element)
        }
        assertEquals(listOfEdgeIds, listOf<Long>(4, 5, 1, 6, 8))
    }

    @Test
    fun NotСonnectedGraph() {
        val graph = WeightedUndirectedGraph().apply() {
            addVertex(0, "A")
            addVertex(1, "B")
            addVertex(2, "C")
            addVertex(3, "D")
            addVertex(4, "E")
            addVertex(5, "F")
            //first component(triangle)
            addEdge(0, 1, 1, 1)
            addEdge(1, 2, 2, 2)
            addEdge(2, 0, 3, 3)
            //second component(triangle)
            addEdge(3, 4, 4, 4)
            addEdge(4, 5, 5, 5)
            addEdge(5, 3, 6, 6)
        }

        val minimalSpanningtree = Kruskal(graph)
        assertEquals(minimalSpanningtree.size, 4)//checking that all vertices from both components are in list
        val listOfEdgeIds = mutableListOf<Long?>()
        minimalSpanningtree.forEach { it ->
            listOfEdgeIds.add(it.element)
        }
        assertEquals(listOfEdgeIds, listOf<Long>(1, 2, 4, 5))
    }
}