package viewmodel

import layouts.YifanHuPlacementStrategy
import model.graph.Graph
import viewmodel.graph.GraphViewModel

class MainScreenViewModel<V, E>(graph: Graph<V, E>) {
    private val representationStrategy = YifanHuPlacementStrategy()
    val graphViewModel = GraphViewModel(graph)

    fun runLayoutAlgorithm(cords: Pair<Int, Int>) {
        representationStrategy.place(cords.first.toDouble(), cords.second.toDouble(), graphViewModel)
    }

    fun updateOnResize(old: Pair<Int, Int>, new: Pair<Int, Int>) {
        representationStrategy.move(old, new, graphViewModel.vertices)
    }
}
