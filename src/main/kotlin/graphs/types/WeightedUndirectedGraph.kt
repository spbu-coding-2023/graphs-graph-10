package graphs.types

import graphs.abstracts.WeightedGraph
import graphs.algo.toAdjacencyList
import java.util.*

internal class WeightedUndirectedGraph<V, E> : WeightedGraph<V, E>() {

    fun findSCC(){
        TODO("Not yet implemented")
    }

    override fun MinimalPathDijkstra(start: V, target: V): List<V> {
        val priorityQueue = PriorityQueue<Pair<V, Long>>(compareBy { it.second })
        val adjList = toAdjacencyList(this)

        val previous = mutableMapOf<V, V>()
        val distances = mutableMapOf<V, Long>()

        adjList.keys.forEach { distances[it] = Long.MAX_VALUE }
        distances[start] = 0
        priorityQueue.add(Pair(start, 0))

        while (priorityQueue.isNotEmpty()) {
            val (currentVertex, currentDistance) = priorityQueue.poll()
            val neighbors = adjList[currentVertex] ?: continue
            if (currentVertex == target) break

            for ((neighbor, weight) in neighbors) {
                val dist = currentDistance + (weight as Long)
                if (dist < distances[neighbor]!!) {
                    distances[neighbor] = dist
                    previous[neighbor] = currentVertex
                    priorityQueue.add(Pair(neighbor, dist))
                }
            }
        }

        val path = mutableListOf<V>()

        // in case if path isn't defined
        var currentNode = target
        if (previous[currentNode] == null)
            return path

        while (currentNode != start) {
            path.add(currentNode)
            currentNode = previous[currentNode] ?: break
        }
        path.add(start)
        path.reverse()
        return path
    }

    override fun MinimalPathFloidBellman(start: V, target: V): WeightedGraph<V, E> {
        TODO("Not yet implemented")
    }

    override fun findCycle(v: V): List<V> {
        TODO("Not yet implemented")
    }
}
