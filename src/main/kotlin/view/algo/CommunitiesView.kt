package view.algo


import graphs.algo.louvain
import viewmodel.graph.GraphViewModel
import kotlinx.coroutines.*

fun drawCommunities(graphViewModel: GraphViewModel) {
    CoroutineScope(Dispatchers.Default).launch {
        println("communities${Thread.currentThread().name}")
        val communities = louvain(graphViewModel.graph)

        val colors = generateRandomColors(communities.size)
        for (i in communities.indices) {
            graphViewModel.vertices.forEach { v ->
                if (v.v.element in communities[i]) {
                    v.color = colors[i]
                }
            }
        }
    }
}
