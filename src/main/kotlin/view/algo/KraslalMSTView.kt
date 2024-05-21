package view.algo


import androidx.compose.ui.graphics.Color
import graphs.algo.Kraskal
import graphs.primitives.Graph
import viewmodel.graph.GraphViewModel

fun <V, E> drawMST(graphViewModel: GraphViewModel<V, E>) {
    val MST = Kraskal(graphViewModel.graph)
    MST.forEach {
        graphViewModel.edges.forEach { e ->
            if (((e.v.v == it.vertices.second) && (e.u.v == it.vertices.first)) || ((e.v.v == it.vertices.first) && (e.u.v == it.vertices.second))) {
                e.color = Color.Cyan
                e.v.color = Color.Cyan
                e.u.color = Color.Cyan
                e.width = 4f

            }

        }
    }
}