package graphs.algo


import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KraskalTest {
    @Test
    fun KraskalTest() {
        val graph = WeightedUndirectedGraph().apply() {
            addEdge(0, 1, 1, 7)
            addEdge(0, 2, 2, 8)
            addEdge(1, 2, 3, 11)
            addEdge(1, 3, 4, 2)
            addEdge(2, 3, 5, 6)
            addEdge(2, 4, 6, 9)
            addEdge(3, 4, 7, 11)
            addEdge(3, 5, 8, )
            addEdge(4, 5, 9)
            addVertex(0, "A")
            addVertex(1, "B")
            addVertex(2, "C")
            addVertex(3, "D")
            addVertex(4, "D")
            addVertex(5, "E")
            addVertex(6, "F")


        }

    }


}