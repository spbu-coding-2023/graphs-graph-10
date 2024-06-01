package view.algo


import androidx.compose.ui.graphics.Color
import graphs.algo.aStar
import graphs.algo.minimalPathDijkstra
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import viewmodel.graph.GraphViewModel

fun CoroutineScope.drawDijkstra(graphViewModel: GraphViewModel,algo: String, onComplete: (String) -> Unit) {
    launch(Dispatchers.Default) {
        println("drawPathOnGraph ${Thread.currentThread().name}")
        val result = drawPathOnGraph(graphViewModel, algo)
        withContext(Dispatchers.Main) {
            onComplete(result)
        }
    }
}

fun drawPathOnGraph(graphViewModel: GraphViewModel, algo: String): String {
    val pickedVertices = graphViewModel.pickedVertices

    if (pickedVertices.size != 2) {
        println("Count of picked vertices != 2")
        return "Count of picked vertices != 2"
    }
    val path: List<Long> = if (algo == "AStar") {
        aStar(
            graphViewModel.graph,
            pickedVertices.first(),
            pickedVertices.last()
        )
    } else {
        minimalPathDijkstra(
            graphViewModel.graph,
            pickedVertices.first(),
            pickedVertices.last()
        )
    }
    for (i in 0..<path.size - 1) {
        graphViewModel.edges.forEach { e ->
            val f = e.v.v.element
            val s = e.u.v.element
            if ((s == path[i] && f == path[i + 1]) || (f == path[i] && s == path[i + 1])) {
                e.color = Color.Cyan
                e.v.color = Color.Cyan
                e.u.color = Color.Cyan
                e.width = 4f
            }
        }
    }
    graphViewModel.pickedVertices.clear()

    if (path.isEmpty())
        return "Can't find path for given vertices."
    return "Path on display"
}
