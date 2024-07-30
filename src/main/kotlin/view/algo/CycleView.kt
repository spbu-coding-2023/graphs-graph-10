package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.findCycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import viewmodel.graph.GraphViewModel

fun CoroutineScope.drawCycles(graphViewModel: GraphViewModel, onComplete: (String) -> Unit) {
    launch(Dispatchers.Default) {
        println("drawCycles ${Thread.currentThread().name}")
        val result = drawCycleOnGraph(graphViewModel)
        withContext(Dispatchers.Main) {
            onComplete(result)
        }
    }
}
fun drawCycleOnGraph(graphViewModel: GraphViewModel): String {
    val t = graphViewModel.pickedVertices
    if (t.size != 1) {
        println("You can pick only one vertex")
        return "You can pick only one vertex"
    }
    val cycle = findCycle(graphViewModel.graph, t.first())
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
    return "Cycle on display"
}
