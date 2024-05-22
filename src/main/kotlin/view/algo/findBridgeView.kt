package view.algo


import androidx.compose.ui.graphics.Color
import graphs.algo.findBridges
import graphs.algo.fordBellman
import viewmodel.graph.GraphViewModel

fun <V, E> drawFindBridge(graphViewModel: GraphViewModel<V, E>) {
    val findBridge = findBridges(graphViewModel.graph)
    findBridge.forEach{
        graphViewModel.edges.forEach {v ->
            if (((v.v.v.element == it.first) && (v.u.v.element == it.second)) || ((v.v.v.element == it.second) && (v.u.v. element == it.first))) {
                v.color = Color.Cyan
                v.width = 15f
            }


        }
    }

}