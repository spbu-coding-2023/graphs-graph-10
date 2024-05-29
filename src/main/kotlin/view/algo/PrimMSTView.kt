package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.searchMstPrim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import viewmodel.graph.GraphViewModel

fun drawMst(graphViewModel: GraphViewModel): Job {
    return CoroutineScope(Dispatchers.Default).launch {
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
}
