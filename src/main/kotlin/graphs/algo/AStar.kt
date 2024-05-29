import graphs.algo.toAdjacencyList
import graphs.primitives.Graph
import java.util.*

fun AStar(graph: Graph, start: Long, goal: Long): List<Long> {
    val openSetContents = mutableSetOf<Long>()
    val cameFrom = mutableMapOf<Long, Long>()
    val gScore = mutableMapOf<Long, Long>().withDefault { Long.MAX_VALUE }
    val fScore = mutableMapOf<Long, Long>().withDefault { Long.MAX_VALUE }
    val adjacencyList = toAdjacencyList(graph)
    val openSet = PriorityQueue<Long>(compareBy { fScore.getValue(it) })
    gScore[start] = 0
    fScore[start] = calculateHeuristic(start, goal, graph)
    openSet.add(start)
    openSetContents.add(start)

    while (openSet.isNotEmpty()) {
        val current = openSet.poll()
        openSetContents.remove(current)

        if (current == goal) {
            return reconstructPath(cameFrom, current)
        }

        val neighbours = adjacencyList[current] ?: continue

        for (neighbor in neighbours) {
            val tentativeGScore = gScore.getValue(current) + (neighbor.second ?: 1L)

            if (tentativeGScore < gScore.getValue(neighbor.first)) {
                cameFrom[neighbor.first] = current
                gScore[neighbor.first] = tentativeGScore
                fScore[neighbor.first] = tentativeGScore + calculateHeuristic(neighbor.first, goal, graph)
                if (neighbor.first !in openSetContents) {
                    openSet.add(neighbor.first)
                    openSetContents.add(neighbor.first)
                }
            }
        }
    }

    return emptyList() // No path found
}

fun reconstructPath(cameFrom: Map<Long, Long>, current: Long): List<Long> {
    val path = mutableListOf<Long>()
    var node: Long? = current
    while (node != null) {
        path.add(node)
        node = cameFrom[node]
    }
    return path.reversed()
}

fun calculateHeuristic(vertex: Long, goal: Long, graph: Graph): Long {
    val adjacencyList = toAdjacencyList(graph)
    val vertexNeighborsCount = adjacencyList[vertex]?.size ?: 0
    val goalNeighborsCount = adjacencyList[goal]?.size ?: 0
    return Math.abs(vertexNeighborsCount - goalNeighborsCount).toLong()
}
