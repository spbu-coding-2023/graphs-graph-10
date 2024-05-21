import graphs.algo.toAdjacencyList
import graphs.primitives.Graph

fun <V, E> louvain(graph: Graph<V, E>): List<Set<V>> {
    val adjList = toAdjacencyList(graph)
    val communities = mutableMapOf<V, Int>()
    adjList.keys.forEach{communities[it] = it.hashCode()}

    var bestModularity : Double
    var bestCommunities = communities.toMap()

    do {
        bestModularity = calculateModularity(graph, bestCommunities)

        var changed = false
        for (node in adjList.keys) {
            for (community in adjList.keys) {
                if (node != community && communities[node] != community) {
                    val newCommunities = bestCommunities.toMutableMap()
                    newCommunities[node] = communities[community]!!
                    val newModularity = calculateModularity(graph, newCommunities)

                    if (newModularity > bestModularity) {
                        bestModularity = newModularity
                        bestCommunities = newCommunities
                        communities[node] = communities[community]!!
                        changed = true
                        break
                    }
                }
            }
            if (changed)
                break
        }
    } while (changed)
    var resultCommunities = mutableListOf<Set<V>>()
    for (value in bestCommunities.values.toSet()) {
        var keys = mutableSetOf<V>()
        for ((key, value1) in bestCommunities) {
            if (value1 == value)
                keys.add(key)
        }

        resultCommunities.add(keys)

    }
    return resultCommunities
}

fun <V, E> calculateModularity(graph: Graph<V, E>, communities: Map<V, Int>): Double {
    val adjList = toAdjacencyList(graph)
    val m = adjList.values.sumOf { it.size }.toDouble() / 2
    var q = 0.0

    for (node1 in adjList.keys) {
        for ((node2,_) in adjList[node1]!!) {
            if (communities[node1] == communities[node2]) {
                q += 1.0 - (adjList[node1]!!.size * adjList[node2]!!.size) / (2 * m)
            }
        }
    }

    return q / (2 * m)
}
