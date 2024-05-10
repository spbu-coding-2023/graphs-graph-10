package graphs.types

import graphs.abstracts.UnweightedGraph
import graphs.algo.toAdjacencyList

internal class UndirectedGraph<V, E> : UnweightedGraph<V, E>() {

    fun findSCC() {
        TODO("Not yet implemented")
    }

    override fun findCycle(v: V): List<V> {
        val adjList = toAdjacencyList(this)
        val visited = mutableSetOf<V>()
        val stack = ArrayDeque<V>()
        var hasCycle = false

        fun searchCycle(vertex: V, parent: V?) {
            visited.add(vertex)
            if (!hasCycle)
                stack.addLast(vertex)
            adjList[vertex]?.forEach {
                if (it !in visited) {
                    searchCycle(it, vertex)
                } else if (parent != it && it == v) {
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
}
