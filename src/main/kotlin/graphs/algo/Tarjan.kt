package graphs.algo


import graphs.primitives.Graph


fun findArticulationVerticesTarjan(graph: Graph): List<Long> {
    val visited = mutableSetOf<Long>()
    val disc = mutableMapOf<Long, Int>()
    val low = mutableMapOf<Long, Int>()
    val parent = mutableMapOf<Long, Long>()
    val articulationPoints = mutableListOf<Long>()

    var time = 0

    fun findTarjanRecursive(u: Long) {
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
