package graphs.algo
import graphs.primitives.Graph
//!! will be fixed soon
fun <V> findBridges(graph: Graph<V, *>): List<Pair<V, V>> {
    val dictGraph = toAdjacencyList(graph)

    val visited = mutableSetOf<V>()
    val low = mutableMapOf<V, Int>()
    val disc = mutableMapOf<V, Int>()
    var time = 0
    val bridges = mutableListOf<Pair<V, V>>()

    fun dfs(u: V, parent: V?) {
        visited.add(u)
        disc[u] = time
        low[u] = time
        time++

        for (v in dictGraph[u] ?: emptyList()) {
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
