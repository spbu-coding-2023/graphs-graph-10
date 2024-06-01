package graphs.algo

import graphs.primitives.Graph

fun louvain(graph: Graph): List<Set<Long>> {
    val adjList = toAdjacencyList(graph)
    val communities = mutableMapOf<Long, Long>()
    adjList.keys.forEach { communities[it] = it.hashCode().toLong() }

    var bestModularity: Double
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
    val resultCommunities = mutableListOf<Set<Long>>()
    for (value in bestCommunities.values.toSet()) {
        val keys = mutableSetOf<Long>()
        for ((key, value1) in bestCommunities) {
            if (value1 == value)
                keys.add(key)
        }

        resultCommunities.add(keys)
    }
    return resultCommunities
}

private fun calculateModularity(graph: Graph, communities: Map<Long, Long>): Double {
    val adjList = toAdjacencyList(graph)
    val m = adjList.values.sumOf { it.size }.toDouble() / 2
    var q = 0.0

    for (node1 in adjList.keys) {
        for ((node2, _) in adjList[node1]!!) {
            if (communities[node1] == communities[node2]) {
                q += 1.0 - (adjList[node1]!!.size * adjList[node2]!!.size) / (2 * m)
            }
        }
    }

    return q / (2 * m)
}
