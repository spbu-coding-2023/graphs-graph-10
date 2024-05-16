package graphs.algo

import graphs.primitives.Graph


fun <V> fordBellman(graph: Graph<V, Long>, source: V, destination: V): Pair<List<V>, Long?>?{
    // Initializing distances for all vertices
    val adjList = toAdjacencyList(graph)
    val distances = mutableMapOf<V, Long>()
    for (i in adjList.keys.distinct()) {
        distances[i] = Int.MAX_VALUE.toLong()
    }
    distances[source] = 0

    // Finding minimal weight for 2 vertex
    for (i in 1 until adjList.size) {
        for (u in adjList.keys) {
            for ((v, weight) in adjList[u] ?: emptyList() ) {
                if (distances[u]!! +  weight!! < distances[v]!!) {
                    distances[v] = distances[u]!! + weight
                }
            }
        }
    }

    for (u in adjList.keys) {
        for ((v, weight) in adjList[u] ?: emptyList() ) {
            if (distances[u]!! + weight!! < distances[v]!!) {
                return null //
            }
        }
    }

    // Restoring the shortest path
    val path = mutableListOf(destination)
    var currentNode = destination
    while (currentNode != source) {
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

    return Pair(path, findWeightPath(graph,path))
}
