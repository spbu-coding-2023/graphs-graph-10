package view.algo


import androidx.compose.ui.graphics.Color
import graphs.algo.findArticulationVerticesTarjan
import viewmodel.graph.GraphViewModel

fun <V, E>drawTarjan(graphViewModel: GraphViewModel<V, E>) {
    val vertices = findArticulationVerticesTarjan(graphViewModel.graph)
    println(vertices)
    for (i in vertices) {
        graphViewModel.vertices.forEach { v ->
            if (i == v.v.element)
                v.color = Color.Cyan
        }
    }
}

