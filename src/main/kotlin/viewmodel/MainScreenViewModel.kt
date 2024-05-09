package viewmodel

import androidx.compose.ui.graphics.Color
import layouts.YifanHuPlacementStrategy
import graphs.primitives.Graph
import viewmodel.graph.GraphViewModel

class MainScreenViewModel<V, E>(var graph: Graph<V, E>) {
    private val representationStrategy = YifanHuPlacementStrategy()
    var graphViewModel = GraphViewModel(graph)

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
