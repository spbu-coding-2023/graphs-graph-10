package view.algo


import androidx.compose.ui.graphics.Color
import graphs.algo.findBridges
import graphs.algo.fordBellman
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewmodel.graph.GraphViewModel

fun drawFindBridge(graphViewModel: GraphViewModel) {
    CoroutineScope(Dispatchers.Default).launch {
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