package view.algo


import graphs.algo.louvain
import viewmodel.graph.GraphViewModel

fun drawCommunities(graphViewModel: GraphViewModel) {
    val communities = louvain(graphViewModel.graph)
    val colors = generateRandomColors(communities.size)
    for (i in 0..<communities.size) {
        graphViewModel.vertices.forEach { v ->
            if (v.v.element in communities[i]) {
                v.color = colors[i]
            }
        }
    }
}

