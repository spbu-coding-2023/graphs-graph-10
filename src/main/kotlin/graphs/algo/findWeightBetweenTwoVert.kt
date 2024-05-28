package graphs.algo

import graphs.primitives.Graph

fun findWeightEdge(graph: Graph, u : Long, v: Long) : Long? {
    val adjList = toAdjacencyList(graph)
    for ((vertex, weight) in adjList[u]  ?: emptyList()) {
        if (vertex == v)
            return weight
    }
    println("Vertices are not connected by edges")
    return null
}
