package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.searchMstPrim
import viewmodel.graph.GraphViewModel

fun <V, E>drawMst(graphViewModel: GraphViewModel<V, E>){
    val mst = searchMstPrim(graphViewModel.graph, graphViewModel.vertices.elementAt(0).v.element)
    for (i in mst) {
        graphViewModel.edges.forEach { e ->
            val f = e.v.v.element
            val s = e.u.v.element
            if ((s == i.first && f == i.second) || (f == i.first && s == i.second)) {
                e.color = Color.Cyan
                e.v.color = Color.Cyan
                e.u.color = Color.Cyan
                e.width = 4f
            }
        }
    }
}