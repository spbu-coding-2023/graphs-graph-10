package view.algo

import graphs.algo.findSCC
import viewmodel.graph.GraphViewModel

fun drawSCC(graphViewModel: GraphViewModel) {
    val communities = findSCC(graphViewModel.graph)
    val colors = generateRandomColors(communities.size)
    for (i in communities.indices) {
        println(communities[i])
        for (j in communities[i].indices) {
            graphViewModel.vertices.forEach { v ->
                if (v.v.element == communities[i][j].element)
                    v.color = colors[i]
            }
        }
    }
}