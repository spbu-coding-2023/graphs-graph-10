package viewmodel

import model.graph.Graph
import viewmodel.graph.GraphViewModel
import viewmodel.graph.RepresentationStrategy

class MainScreenViewModel<V, E>(graph: Graph<V, E>, private val representationStrategy: RepresentationStrategy,) {
    val graphViewModel = GraphViewModel(graph)

    fun updateOnResize(width: Int, height: Int) {
        representationStrategy.place(width.toDouble(), height.toDouble(), graphViewModel.vertices)
    }

}