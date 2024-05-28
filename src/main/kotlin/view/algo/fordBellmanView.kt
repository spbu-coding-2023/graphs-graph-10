package view.algo

import androidx.compose.ui.graphics.Color
import graphs.algo.fordBellman
import viewmodel.graph.GraphViewModel

import kotlinx.coroutines.*
import androidx.compose.runtime.*

fun CoroutineScope.drawFordBellman(graphViewModel: GraphViewModel, onComplete: (String) -> Unit) {
    launch(Dispatchers.Default) {
        println("drawFordBellman ${Thread.currentThread().name}")
        val result = fordBellman(graphViewModel)
        withContext(Dispatchers.Main) {
            onComplete(result)
        }
    }
}

fun fordBellman(graphViewModel: GraphViewModel): String {
    val pickedVertices = graphViewModel.pickedVertices

    if (pickedVertices.size != 2) {
        println("Count of picked vertices != 2")
        return "Count of picked vertices != 2"
    }

    val path = fordBellman(
        graphViewModel.graph,
        pickedVertices.first(),
        pickedVertices.last()
    )?.first

    if (path == null || path.isEmpty()) {
        return "Can't find path for given vertices."
    }

    for (i in 0 until path.size - 1) {
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

    if (path.isEmpty()) {
        return "Can't find path for given vertices."
    }

    return "Path on display"
}

