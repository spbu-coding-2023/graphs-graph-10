package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.findBridges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import viewmodel.graph.GraphViewModel

fun drawFindBridge(graphViewModel: GraphViewModel): Job {
    return CoroutineScope(Dispatchers.Default).launch {
        println("bridges${Thread.currentThread().name}")
        val findBridge = findBridges(graphViewModel.graph)
        findBridge.forEach {
            graphViewModel.edges.forEach { v ->
                if (((v.v.v.element == it.first) && (v.u.v.element == it.second)) || ((v.v.v.element == it.second) && (v.u.v.element == it.first))) {
                    v.color = Color.Cyan
                    v.width = 15f
                }
            }
        }
    }
}
