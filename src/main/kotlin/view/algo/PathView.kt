package view.algo

import androidx.compose.ui.graphics.Color
import graphs.types.WeightedUndirectedGraph
import viewmodel.graph.GraphViewModel

fun <V, E> drawPathOnGraph(graphViewModel: GraphViewModel<V, E>) {
    val weightedUndirectedGraph = graphViewModel.graph as WeightedUndirectedGraph<V, E>
    val pickedVertices = graphViewModel.pickedVertices

    if (pickedVertices.size != 2) {
        println("Path not found")
        return
    }
    val path = weightedUndirectedGraph.MinimalPathDijkstra(
        pickedVertices.first(),
        pickedVertices.last()
    )

    for (i in 0..<path.size-1) {
        graphViewModel.edges.forEach { e ->
            val f = e.v.v.element
            val s = e.u.v.element
            if ((s == path[i] && f == path[i+1]) || (f == path[i] && s == path[i+1])) {
                e.color = Color.Cyan
                e.v.color = Color.Cyan
                e.u.color = Color.Cyan
                e.width = 4f
            }
        }
    }
    graphViewModel.pickedVertices.clear()
}
