package integration

import androidx.compose.ui.graphics.Color
import graphs.algo.findBridges
import graphs.types.UndirectedGraph
import org.junit.jupiter.api.Test
import view.algo.drawFindBridge
import viewmodel.MainScreenViewModel
import kotlin.test.assertEquals

class FindBridgeIntegrationTest() {
    private val sampleGraph = UndirectedGraph().apply {
        addEdge(1, 0, 0)
        addEdge(2, 1, 1)
        addEdge(2, 0, 2)
        addEdge(0, 3, 3)
        addEdge(3, 4, 4)
    }
    private val mainViewModel = MainScreenViewModel(sampleGraph)
    @Test
    fun FindBridgeIntegrationTest() {

        mainViewModel.runLayoutAlgorithm(Pair(800, 600))

        drawFindBridge(mainViewModel.graphViewModel)
        val bridges = findBridges(sampleGraph)

        var countBridges = 0
        for (i in bridges.indices) {
            mainViewModel.graphViewModel.edges.forEach { e ->
                val u = e.u.v.element
                val v = e.v.v.element
                if ((Pair(u, v) == bridges[i]) || (Pair(v, u) == bridges[i]) )
                    if (e.color == Color.Cyan)
                        countBridges ++

            }
        }
        assertEquals(countBridges, bridges.size, "The count of extended edges does not match the count of real bridges")
    }


}