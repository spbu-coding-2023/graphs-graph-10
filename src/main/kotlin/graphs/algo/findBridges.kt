package graphs.algo
import graphs.primitives.Graph
//!! will be fixed soon
fun findBridges(graph: Graph) : MutableList<Pair<Long, Long>> {
    val dictGraph = toAdjacencyList(graph)
    val visited = mutableSetOf<Long>()
    val low = mutableMapOf<Long, Int>()
    val disc = mutableMapOf<Long, Int>()
    var time = 0
    val bridges = mutableListOf<Pair<Long, Long>>()

    fun dfs(u: Long, parent: Long?) {
        visited.add(u)
        disc[u] = time
        low[u] = time
        time++

        for ((v, _) in dictGraph[u] ?: emptyList()) {
            if (v !in visited) {
                dfs(v, u)
                low[u] = minOf(low[u]!!, low[v]!!)
                if (low[v]!! > disc[u]!!) {
                    bridges.add(v to u)
                }
            } else if (v != parent) {
                low[u] = minOf(low[u]!!, disc[v]!!)
            }
        }
    }

    for (vertex in dictGraph.keys) {
        if (vertex !in visited) {
            dfs(vertex, null)
        }
    }

    return bridges
}
