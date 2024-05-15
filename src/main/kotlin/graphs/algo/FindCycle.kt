package graphs.algo

import graphs.primitives.Graph

fun <V, E> findCycle(graph: Graph<V, E>, v: V): List<V> {
    val adjList = toAdjacencyList(graph)
    val visited = mutableSetOf<V>()
    val stack = ArrayDeque<V>()
    var hasCycle = false

    fun searchCycle(vertex: V, parent: V?) {
        visited.add(vertex)
        if (!hasCycle)
            stack.addLast(vertex)
        adjList[vertex]?.forEach {
            val (currentVertex, _) = it
            if (currentVertex !in visited) {
                searchCycle(currentVertex, vertex)
            } else if (parent != currentVertex && currentVertex == v) {
                if (!hasCycle) stack.addLast(v)
                hasCycle = true
                return
            }
        }
        if (!hasCycle)
            stack.removeLast()
        return
    }
    searchCycle(v, null)
    return stack.toList()
}
