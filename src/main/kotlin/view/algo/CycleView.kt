package view.algo

import androidx.compose.ui.graphics.Color
import viewmodel.graph.GraphViewModel

fun <V, E> drawCycleOnGraph(graphViewModel: GraphViewModel<V, E>): String {
    val t = graphViewModel.pickedVertices
    if (t.size != 1) {
        println("You can pick only one vertex")
        return "You can pick only one vertex"
    }
    val cycle = graphViewModel.graph.findCycle(t.first())
    if (cycle.isEmpty()) {
        return "No cycles for given vertex"
    }
    for (i in 0..<cycle.size-1) {
        graphViewModel.edges.forEach { e ->
            val f = e.v.v.element
            val s = e.u.v.element
            if ((s == cycle[i] && f == cycle[i+1]) || (f == cycle[i] && s == cycle[i+1])) {
                e.color = Color.Cyan
                e.v.color = Color.Cyan
                e.u.color = Color.Cyan
                e.width = 4f
            }
        }
    }
    graphViewModel.pickedVertices.clear()
    return ""
}
