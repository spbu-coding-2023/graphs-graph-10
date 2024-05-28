package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.Kruskal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import viewmodel.graph.GraphViewModel

fun drawKruskalMST(graphViewModel: GraphViewModel): Job {
    return CoroutineScope(Dispatchers.Default).launch {
        val MST = Kruskal(graphViewModel.graph)
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
}
