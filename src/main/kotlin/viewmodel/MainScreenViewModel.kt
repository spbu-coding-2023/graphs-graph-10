package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import layouts.YifanHuPlacementStrategy
import graphs.primitives.Graph
import viewmodel.graph.GraphViewModel

class MainScreenViewModel(var graph: Graph) {
    private val representationStrategy = YifanHuPlacementStrategy()
    var graphViewModel = GraphViewModel(graph)
    var scale = mutableStateOf(1f)
    var offset = mutableStateOf(DpOffset.Zero)
    var displayWeight = mutableStateOf(false)
    var runLayout = false

    fun runLayoutAlgorithm(cords: Pair<Int, Int>) {
        representationStrategy.place(cords.first.toDouble(), cords.second.toDouble(), graphViewModel)
    }

    fun updateOnResize(old: Pair<Int, Int>, new: Pair<Int, Int>) {
        representationStrategy.move(old, new, graphViewModel.vertices)
    }

    fun restoreGraphState() {
        graphViewModel.edges.forEach { e ->
            e.color = Color.Black
            e.width = 1f
        }
        graphViewModel.vertices.forEach { v ->
            v.color = Color.Gray
        }
        graphViewModel.pickedVertices.clear()
    }
}
