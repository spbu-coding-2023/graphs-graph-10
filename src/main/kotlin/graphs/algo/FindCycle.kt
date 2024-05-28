package graphs.algo

import graphs.primitives.Graph

fun findCycle(graph: Graph, v: Long): List<Long> {
    val adjList = toAdjacencyList(graph)
    val visited = mutableSetOf<Long>()
    val stack = ArrayDeque<Long>()
    var hasCycle = false

    fun searchCycle(vertex: Long, parent: Long?) {
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
