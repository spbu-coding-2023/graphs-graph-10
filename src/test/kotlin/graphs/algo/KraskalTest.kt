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
        assertEquals(minimalSpanningtree.size, 6)
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
}