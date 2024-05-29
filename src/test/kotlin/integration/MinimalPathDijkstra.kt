package integration

import androidx.compose.ui.graphics.Color
import graphs.algo.minimalPathDijkstra
import graphs.types.UndirectedGraph
import org.junit.jupiter.api.Test
import view.algo.drawPathOnGraph
import viewmodel.MainScreenViewModel
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * MinimalPathDijkstraIntegration class demonstrates the integration of Dijkstra's algorithm
 * with a graph-based user interface. It initializes a simple graph and runs tests to ensure
 * the shortest path is found and correctly displayed.
 */
class MinimalPathDijkstraIntegration {
    /**
     * The graph instance, an undirected graph with three vertices and two edges.
     */
    private val graph = UndirectedGraph().apply {
        addVertex(1)
        addVertex(2)
        addVertex(3)
        addEdge(1, 2, 1)
        addEdge(2, 3, 2)
    }

    /**
     * The main view model for the graph, initialized with the graph instance.
     */
    private val mainViewModel = MainScreenViewModel(graph)

    /**
     * Test method to validate the integration of Dijkstra's algorithm.
     * It performs the following steps:
     * - Runs the layout algorithm with specified dimensions.
     * - Simulates the selection of two vertices (1 and 3).
     * - Finds the shortest path between the selected vertices.
     * - Draws the path on the graph.
     * - Verifies that the path was drawn correctly by checking the color of the edges.
     */
    @Test
    fun MinimalPathDijkstraIntegrationTest() {
        // Run layout algorithm
        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        // simulate picking two vertexes
        mainViewModel.graphViewModel.pickedVertices.add(1)
        mainViewModel.graphViewModel.pickedVertices.add(3)

        // find path and draw it
        val path = minimalPathDijkstra(graph, 1, 3)
        assertNotEquals(0, path.size)
        drawPathOnGraph(mainViewModel.graphViewModel, "Dijkstra")

        // check if path was drawn correctly
        var countColoredVertexes = 0
        for (i in 0..<path.size-1) {
            mainViewModel.graphViewModel.edges.forEach { e ->
                val f = e.u.v.element
                val s = e.v.v.element
                if (f == path[i] && s == path[i+1]) {
                    if (e.color == Color.Cyan)
                        countColoredVertexes++
                }
            }
        }
        assertEquals(countColoredVertexes, 2)
    }
}
