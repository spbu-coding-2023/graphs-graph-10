package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.findArticulationVerticesTarjan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewmodel.graph.GraphViewModel

fun drawTarjan(graphViewModel: GraphViewModel) {
    CoroutineScope(Dispatchers.Default).launch {
        val vertices = findArticulationVerticesTarjan(graphViewModel.graph)
        println(vertices)
        for (i in vertices) {
            graphViewModel.vertices.forEach { v ->
                if (i == v.v.element)
                    v.color = Color.Cyan
            }
        }
    }
}
