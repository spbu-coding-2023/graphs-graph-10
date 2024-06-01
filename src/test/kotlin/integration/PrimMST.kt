package integration

import androidx.compose.ui.graphics.Color
import graphs.algo.searchMstPrim
import graphs.types.WeightedUndirectedGraph
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import view.algo.drawMst
import viewmodel.MainScreenViewModel
import kotlin.test.assertEquals

class MSTPrimIntegrationTest {

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
        val tree = searchMstPrim(graph, graph.vertices.elementAt(0).element)
        assertEquals(5, tree.size)

        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        drawMst(mainViewModel.graphViewModel).join()

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
        val tree = searchMstPrim(graph, graph.vertices.elementAt(0).element)
        assertEquals(5, tree.size)

        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        drawMst(mainViewModel.graphViewModel).join()

        var counterOfMatchedIds = 0
        mainViewModel.graphViewModel.edges.forEach { e ->
            if (e.color == Color.Cyan) {
                tree.forEach {
                    if (e.e.vertices.first.element == it.first && e.e.vertices.second.element == it.second) {
                        counterOfMatchedIds++
                    }
                }
            }
        }
        assertEquals(counterOfMatchedIds, mainViewModel.graph.vertices.size - 1)
    }
}
