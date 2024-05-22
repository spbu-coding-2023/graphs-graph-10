package graphs.algo

import graphs.primitives.Graph


fun <V, E> fordBellman(graph: Graph<V, E>, start: V, end: V): Pair<List<V>, Long?>?{

    val adjList = toAdjacencyList(graph)
    val distances = mutableMapOf<V, Long>()
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
                if (distances[u]!! +  weight < distances[v]!!) {
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
    
    val path = mutableListOf(end)
    var currentNode = end
    while (currentNode != start) {
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
