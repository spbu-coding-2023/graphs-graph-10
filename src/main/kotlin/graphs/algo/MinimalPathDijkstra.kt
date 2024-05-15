package graphs.algo

import graphs.primitives.Graph
import java.util.*

fun <V, E> MinimalPathDijkstra(graph: Graph<V, E>, start: V, target: V): List<V> {
    val priorityQueue = PriorityQueue<Pair<V, Long>>(compareBy { it.second })
    val adjList = toAdjacencyList(graph)
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
            val dist = currentDistance + ((weight ?: 1L) as Long)
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
