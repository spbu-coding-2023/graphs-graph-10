package graphs.algo


import graphs.primitives.Graph


fun <V, E> findArticulationVerticesTarjan(graph: Graph<V, E>): List<V> {
    val visited = mutableSetOf<V>()
    val disc = mutableMapOf<V, Int>()
    val low = mutableMapOf<V, Int>()
    val parent = mutableMapOf<V, V>()
    val articulationPoints = mutableListOf<V>()

    var time = 0

    fun findTarjanRecursive(u: V) {
        visited.add(u)
        disc[u] = time
        low[u] = time
        time++

        var children = 0
        for (edge in graph.edges) {
            if (edge.vertices.first.element == u || edge.vertices.second.element == u) {
                val v = if (edge.vertices.first.element == u) edge.vertices.second.element
                        else edge.vertices.first.element
                if (v !in visited) {
                    children++
                    parent[v] = u
                    findTarjanRecursive(v)

                    low[u] = minOf(low[u] ?: Int.MAX_VALUE, low[v] ?: Int.MAX_VALUE)

                    if (parent[u] == null && children > 1) {
                        articulationPoints.add(u)
                    }
                    if (parent[u] != null && (low[v] ?: 0) >= disc[u]!!) {
                        articulationPoints.add(u)
                    }
                } else if (v != parent[u]) {
                    low[u] = minOf(low[u] ?: Int.MAX_VALUE, disc[v] ?: 0)
                }
            }
        }
    }

    for (vertex in graph.vertices) {
        if (vertex.element !in visited)
            findTarjanRecursive(vertex.element)
    }

    return articulationPoints
}
