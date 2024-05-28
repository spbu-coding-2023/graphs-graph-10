

import androidx.compose.ui.graphics.Color
import graphs.algo.Kruskal
import graphs.algo.MinimalPathDijkstra
import graphs.types.UndirectedGraph
import graphs.types.WeightedUndirectedGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import view.algo.drawKruskalMST
import viewmodel.MainScreenViewModel
import kotlin.test.assertEquals



class MSTKruskalIntegrationTest {

    private val graph = WeightedUndirectedGraph().apply {
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
    val mainViewModel = MainScreenViewModel(graph)

    @Test
    fun checkingAmountOfColouredInMSTEdges() = runBlocking {
        val minimalSpanningTree = Kruskal(graph)
        assertEquals(5, minimalSpanningTree.size)

        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        drawKruskalMST(mainViewModel.graphViewModel).join()

        var countColoredEdges = 0
        mainViewModel.graphViewModel.edges.forEach { e ->
            if (e.color == Color.Cyan) {
                countColoredEdges++
            }
        }
        assertEquals(countColoredEdges, mainViewModel.graph.vertices.size - 1)
    }



    @Test
    fun checkingIdsOfColouredInMSTEdges() = runBlocking {
        val minimalSpanningTree = Kruskal(graph)
        assertEquals(5, minimalSpanningTree.size)

        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        drawKruskalMST(mainViewModel.graphViewModel).join()

        var counterOfMatchedIds = 0
        mainViewModel.graphViewModel.edges.forEach { e ->
            if (e.color == Color.Cyan) {
                minimalSpanningTree.forEach {
                    if (e.e.element == it.element) {
                        counterOfMatchedIds++
                    }
                }
            }
        }
        assertEquals(counterOfMatchedIds, mainViewModel.graph.vertices.size - 1)
    }
}
