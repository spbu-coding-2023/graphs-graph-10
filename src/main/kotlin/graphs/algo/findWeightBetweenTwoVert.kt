package graphs.algo

import graphs.primitives.Graph

fun <V, E> findWeightEdge(graph: Graph<V, E>, u : V, v: V) : Long? {
    val adjList = toAdjacencyList(graph)
    for ((vertex, weight) in adjList[u]  ?: emptyList()) {
        if (vertex == v)
            return weight
    }
    println("Vertices are not connected by edges")
    return null
}