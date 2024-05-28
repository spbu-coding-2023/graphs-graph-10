package integration

import graphs.types.UndirectedGraph
import org.junit.jupiter.api.Test
import viewmodel.MainScreenViewModel
import androidx.compose.ui.graphics.Color
import graphs.algo.Kruskal
import graphs.algo.MinimalPathDijkstra
import graphs.types.WeightedUndirectedGraph
import view.algo.drawKruskalMST
import view.algo.drawPathOnGraph
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class MSTKruskalIntegrationTest {
    /**
     * The graph instance, an undirected graph with three vertices and two edges.
     */

    private val graph = WeightedUndirectedGraph().apply() {
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
    private val mainViewModel = MainScreenViewModel(graph)

    @Test
    fun checkingamountOfColouredInMSTedges() {
        val minimalSpanningtree = Kruskal(graph)
        assertEquals(5, minimalSpanningtree.size)

        // Run layout algorithm
        mainViewModel.runLayoutAlgorithm(Pair(800, 600))
        drawKruskalMST(mainViewModel.graphViewModel)
        var countColoredEdges = 0

        mainViewModel.graphViewModel.edges.forEach { e ->
            if (e.color == Color.Cyan) {
                countColoredEdges++
            }

        }
        assertEquals(countColoredEdges, 5)
        assertEquals(countColoredEdges, mainViewModel.graph.vertices.size - 1)
    }

    @Test
    fun checkingIdsOfColouredInMSTedges() {
        val minimalSpanningtree = Kruskal(graph)
        assertEquals(5, minimalSpanningtree.size)

        // Run layout algorithm
        mainViewModel.runLayoutAlgorithm(Pair(800, 600))
        drawKruskalMST(mainViewModel.graphViewModel)
        var IdMatching: Boolean = false
        var counterOfMatchedIds: Int = 0
        mainViewModel.graphViewModel.edges.forEach { e ->
            if (e.color == Color.Cyan) {
                minimalSpanningtree.forEach {
                    if (e.e.element == it.element) {
                        IdMatching = true
                        counterOfMatchedIds++
                    }
                }
                assertEquals(true, IdMatching)
            }
            IdMatching = false
        }
        assertEquals(counterOfMatchedIds, mainViewModel.graph.vertices.size - 1)
        assertEquals(counterOfMatchedIds, 5)
    }
}

