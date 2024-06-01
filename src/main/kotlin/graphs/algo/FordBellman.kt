package graphs.algo

import graphs.primitives.Graph

fun fordBellman(graph: Graph, start: Long, end: Long): Pair<List<Long>, Long?>? {

    val adjList = toAdjacencyList(graph)
    val distances = mutableMapOf<Long, Long>()
    for (i in adjList.keys.distinct()) {
        distances[i] = Int.MAX_VALUE.toLong()
    }
    distances[start] = 0

    for (i in 1 until adjList.size) {
        for (u in adjList.keys) {
            for ((v, weight) in adjList[u] ?: emptyList() ) {
                if (weight == null) {
                    println("The Ford-Bellman algorithm only works with weighted graphs")
                    return null
                }
                if (distances[u]!! + weight < distances[v]!!) {
                    distances[v] = distances[u]!! + weight
                }
            }
        }
    }

    for (u in adjList.keys) {
        for ((v, weight) in adjList[u] ?: emptyList() ) {
            if (distances[u]!! + weight!! < distances[v]!!) {
                return null // negative cycle
            }
        }
    }

    val path = mutableListOf(end)
    val countVertices = graph.vertices.size
    var currentNode = end
    while (currentNode != start && path.size <= countVertices) {
        for (u in adjList.keys) {
            for ((v, weight) in adjList[u] ?: emptyList()) {
                if (v == currentNode && distances[u]!! + weight!! == distances[v]!!) {
                    path.add(0, u)
                    currentNode = u
                    break
                }
            }
        }
    }

    return if (currentNode == start) Pair(path, findWeightPath(graph, path)) else null
    //if there is  negative cycle, then we return null
}
