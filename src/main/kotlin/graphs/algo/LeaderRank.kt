package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import graphs.primitives.Vertex
import graphs.types.DirectedGraph
import graphs.types.WeightedDirectedGraph
import kotlin.math.abs

fun <V, E> LeaderRank(G: Graph<V, E>, d: Double, epsilon: Double): List<Pair<Vertex<V>, Double>> {
    val adjacencyList = toAdjacencyList(G)
    val n = G.vertices.size
    var R = mutableMapOf<Vertex<V>, Double>()
    G.vertices.forEach { vertex -> R[vertex] = 1.0 / n }

    val deg = buildOutDegreeMatrix(adjacencyList)

    var diff = Double.MAX_VALUE
    while (diff >= epsilon) {
        val R_new = mutableMapOf<Vertex<V>, Double>()
        G.vertices.forEach { v ->
            var sum = 0.0
            G.vertices.forEach { u ->
                val neighbors = adjacencyList[u.element] ?: mutableSetOf()
                val weight = neighbors.find { it.first == v.element }?.second?.toDouble() ?: 0.0
                sum += (d * weight / (deg[u.element]?.toDouble() ?: 1.0)) * (R[u] ?: 0.0)
            }
            R_new[v] = (1 - d) / n + sum
        }

        diff = 0.0
        G.vertices.forEach { v ->
            diff += abs(R_new[v]!! - R[v]!!)
        }

        R = R_new
    }

    val sortedRatings = R.toList().sortedByDescending { it.second }
    return sortedRatings
}

fun <V> buildOutDegreeMatrix(adjacencyList: Map<V, Set<Pair<V, Long?>>>): Map<V, Int> {
    val deg = mutableMapOf<V, Int>()

    for ((v, neighbors) in adjacencyList) {
        deg[v] = neighbors.size
    }

    return deg
}

fun <V> printAdjacencyMatrix(adjacencyMatrix: Map<Vertex<V>, Map<Vertex<V>, Long?>>) {
    for ((vertex, neighbors) in adjacencyMatrix) {
        println("Vertex ${vertex.element}:")
        for ((neighbor, edge) in neighbors) {
            println("  Neighbor: ${neighbor.element}, Edge weight: ${edge}")
        }
    }
}
